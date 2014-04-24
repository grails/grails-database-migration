dataSource {
	pooled = true
	driverClassName = 'com.mysql.jdbc.Driver'
	username = 'migrationtest'
	password = 'migrationtest'
	url = 'jdbc:mysql://localhost/migrationtest'
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect
}

hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = false
	cache.region.factory_class = System.getProperty('hibernatePluginVersion')?.startsWith(':hibernate4') ? 'org.hibernate.cache.ehcache.EhCacheRegionFactory' : 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
	format_sql = true
	use_sql_comments = true
}

dataSource_reports {
	pooled = true
	driverClassName = 'com.mysql.jdbc.Driver'
	username = 'migrationtest'
	password = 'migrationtest'
	url = 'jdbc:mysql://localhost/migrationtest_reports'
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect
}
