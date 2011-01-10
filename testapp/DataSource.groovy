dataSource {
	pooled = true
	driverClassName = 'com.mysql.jdbc.Driver'
	username = 'migrationtest'
	password = 'migrationtest'
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect
}

hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

environments {
	development {
		dataSource {
			url = 'jdbc:mysql://localhost/migrationtest'
		}
	}
	test {
		dataSource {
			driverClassName = 'org.hsqldb.jdbcDriver'
			url = 'jdbc:hsqldb:mem:testDb'
			username = 'sa'
			password = ''
			dialect = org.hibernate.dialect.HSQLDialect
			dbCreate = 'update'
		}
	}
	production {
		dataSource {
			url = 'jdbc:mysql://localhost/migrationtest'
		}
	}
}

