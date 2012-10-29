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
	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
	format_sql = true
	use_sql_comments = true
}

