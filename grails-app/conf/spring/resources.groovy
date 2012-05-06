import grails.plugin.databasemigration.test.DelayedDataSource
import grails.plugin.databasemigration.test.DelayedSessionFactoryBean

import org.springframework.jdbc.support.lob.DefaultLobHandler
import org.springframework.orm.hibernate3.HibernateTransactionManager

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


    'sessionFactory_secondary'(DelayedSessionFactoryBean) {
   		dataSource = ref('dataSourceSecondary')
   		entityInterceptor = ref('entityInterceptor')
   		grailsApplication = application
   		hibernateProperties = ref('hibernateProperties')
   		lobHandler = ref('lobHandlerDetector')
   	}

    'transactionManager_secondary'(HibernateTransactionManager) {
        sessionFactory = ref('sessionFactory_secondary')
    }

    dataSourceSecondary(DelayedDataSource) {
   		dataSourceConfig = application.config.dataSource_secondary
   	}
}
