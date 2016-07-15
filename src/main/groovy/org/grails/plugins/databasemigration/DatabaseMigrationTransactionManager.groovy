package org.grails.plugins.databasemigration

import grails.transaction.GrailsTransactionTemplate
import org.springframework.context.ApplicationContext
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

/**
 * Created by Jim on 7/15/2016.
 */
class DatabaseMigrationTransactionManager {

    final String dataSource
    final ApplicationContext applicationContext

    DatabaseMigrationTransactionManager(ApplicationContext applicationContext, String dataSource) {
        this.dataSource = dataSource
        this.applicationContext = applicationContext
    }

    PlatformTransactionManager getTransactionManager() {
        String dataSource = this.dataSource ?: "dataSource"
        String beanName = "transactionManager"
        if (dataSource != "dataSource") {
            beanName += "_${dataSource}"
        }
        applicationContext.getBean(beanName, PlatformTransactionManager)
    }

    void withTransaction(Closure callable) {
        new GrailsTransactionTemplate(transactionManager, new DefaultTransactionDefinition()).execute(callable)
    }
}
