import grails.plugin.databasemigration.test.DelayedDataSource
import grails.plugin.databasemigration.test.DelayedSessionFactoryBean
import org.springframework.jdbc.support.lob.DefaultLobHandler

// for testing only; beans are used to delay SessionFactory/DataSource creation
beans = {

	lobHandlerDetector(DefaultLobHandler)

	sessionFactory(DelayedSessionFactoryBean) {
		dataSource = ref('dataSource')
		entityInterceptor = ref('entityInterceptor')
		grailsApplication = ref('grailsApplication', true)
		hibernateProperties = ref('hibernateProperties')
		lobHandler = ref('lobHandlerDetector')
	}

	dataSource(DelayedDataSource) {
		dataSourceConfig = getBean('grailsApplication').config.dataSource
	}
}
