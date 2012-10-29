import grails.plugin.databasemigration.test.DelayedDataSource

import java.lang.reflect.Field
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

import org.codehaus.groovy.grails.orm.hibernate.ConfigurableLocalSessionFactoryBean
import org.hibernate.SessionFactory
import org.springframework.jdbc.support.lob.DefaultLobHandler
import org.springframework.util.ReflectionUtils

// for testing only; beans are used to delay SessionFactory/DataSource creation
beans = {

	lobHandlerDetector(DefaultLobHandler)

	sessionFactory(DelayedSessionFactoryBean) {
		dataSource = ref('dataSource')
		entityInterceptor = ref('entityInterceptor')
		grailsApplication = application
		hibernateProperties = ref('hibernateProperties')
		lobHandler = ref('lobHandlerDetector')
	}

	dataSource(DelayedDataSource) {
		dataSourceConfig = application.config.dataSource
	}
}

/**
 * Only used for testing; see http://burtbeckwith.com/blog/?p=312
 *
 * @author <a href='mailto:burt@burtbeckwith.com'>Burt Beckwith</a>
 */
class DelayedSessionFactoryBean extends ConfigurableLocalSessionFactoryBean {

	private boolean _initialized
	private SessionFactory _realSessionFactory
	private SessionFactory _proxied

	@Override
	void afterPropertiesSet() {
		// do nothing for now, lazy init on first access
	}

	@Override
	SessionFactory getObject() {
		_proxied = Proxy.newProxyInstance(SessionFactory.classLoader, [SessionFactory] as Class[], new InvocationHandler() {
			Object invoke(proxy, Method method, Object[] args) {
				if ('hashCode'.equals(method.getName())) {
					return 1
				}
				if ('equals'.equals(method.getName())) {
					return _proxied.is(args[0])
				}

				initialize()
				method.invoke _realSessionFactory, args
			}
		})
		_proxied
	}

	SessionFactory getRealSessionFactory() {
		initialize()
		_realSessionFactory
	}

	private synchronized void initialize() {
		if (_initialized) {
			return
		}

		_realSessionFactory = wrapSessionFactoryIfNecessary(buildSessionFactory())

		Field field = ReflectionUtils.findField(getClass(), 'sessionFactory')
		field.accessible = true
		field.set this, _realSessionFactory

		afterSessionFactoryCreation()

		_initialized = true
	}
}
