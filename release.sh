rm -rf target/release
mkdir target/release
cd target/release
git clone git@github.com:grails-plugins/grails-database-migration.git
cd grails-database-migration
grails clean
grails compile
grails compile
#grails publish-plugin --noScm --snapshot --stacktrace
grails publish-plugin --noScm --stacktrace
