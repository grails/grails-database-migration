#!/bin/bash

# Creates a test app and installs the plugin, then changes domain classes and does the required
# migrations. Change the hard-coded values in the variables below for your local system to use.
# Create a MySQL database 'migrationtest' and another called 'migrationtest_reports' for the
# multi-datasource tests; both databases need a 'migrationtest' user with password 'migrationtest'.
TESTAPP_SRC_DIR="$( cd "$( dirname "$0" )" && pwd )"
PLUGIN_DIR="$( cd "$TESTAPP_SRC_DIR"/.. && pwd )"
APP_NAME="migrationtests"
DB_NAME="migrationtest"
DB_REPORTS_NAME="migrationtest_reports"
DB_USERNAME="migrationtest"
DB_PASSWORD="migrationtest"
PLUGIN_VERSION="1.4.0-SNAPSHOT"

APP_DIR_PARENT="$PLUGIN_DIR/target"
APP_DIR="$APP_DIR_PARENT/$APP_NAME"

verifyExitCode() {
	if [ $1 -ne 0 ]; then
		echo "ERROR: $2 failed with exit code $1"
		exit $1
	fi
}

add_mysql_user() {
	local dbname=$1
	local username=$2
	local password=$3
mysql <<EOF
GRANT USAGE ON $dbname.* TO '$username'@'%' IDENTIFIED BY '$password' WITH GRANT OPTION;
GRANT ALL ON $dbname.* TO '$username'@'%';
GRANT USAGE ON $dbname.* TO '$username'@'localhost' IDENTIFIED BY '$password' WITH GRANT OPTION;
GRANT ALL ON $dbname.* TO '$username'@'localhost';
EOF
 	[[ $? -eq 0 ]] || ( echo "Cannot add users. Add mysql root username & password to $HOME/.my.cnf for development use." && exit 1 )
}

set -x

[ ! -e "$APP_DIR_PARENT" ] && mkdir -p "$APP_DIR_PARENT"
cd "$APP_DIR_PARENT"

[ -e "$APP_NAME" ] && rm -rf "$APP_NAME"
grails create-app "$APP_NAME" --stacktrace
verifyExitCode $? "create-app"

cd "$TESTAPP_SRC_DIR"

# initial domain classes
mkdir "$APP_DIR/grails-app/domain/migrationtests"
cp Product.v1.groovy "$APP_DIR/grails-app/domain/migrationtests/Product.groovy"
cp Order.v1.groovy "$APP_DIR/grails-app/domain/migrationtests/Order.groovy"
cp OrderItem.v1.groovy "$APP_DIR/grails-app/domain/migrationtests/OrderItem.groovy"
cp Report.groovy "$APP_DIR/grails-app/domain/migrationtests/"

# config
cp BuildConfig.groovy "$APP_DIR/grails-app/conf"
cp Config.groovy "$APP_DIR/grails-app/conf"
cp DataSource.groovy "$APP_DIR/grails-app/conf"

# scripts
cp PopulateData.groovy "$APP_DIR/scripts/"
cp VerifyData.groovy "$APP_DIR/scripts/"

# drop and create db
mysql -e "drop database if exists $DB_NAME; create database $DB_NAME DEFAULT CHARACTER SET = utf8;"
verifyExitCode $? "drop/create database"
add_mysql_user $DB_NAME $DB_USERNAME $DB_PASSWORD

# drop and create reports db
mysql -e "drop database if exists $DB_REPORTS_NAME; create database $DB_REPORTS_NAME DEFAULT CHARACTER SET = utf8;"
verifyExitCode $? "drop/create database"
add_mysql_user $DB_REPORTS_NAME $DB_USERNAME $DB_PASSWORD

cd "$APP_DIR"

grails compile --stacktrace

# create the initial changelog and export to db
grails dbm-create-changelog --stacktrace
verifyExitCode $? "dbm-create-changelog"

# create the initial changelog for reports datasource and export to db
grails dbm-create-changelog --dataSource=reports --stacktrace
verifyExitCode $? "dbm-create-changelog for reports datasource"


grails dbm-generate-gorm-changelog initial.groovy --add --stacktrace
verifyExitCode $? "dbm-generate-gorm-changelog"

grails dbm-generate-gorm-changelog initialReports.groovy --add --stacktrace --dataSource=reports
verifyExitCode $? "dbm-generate-gorm-changelog for reports datasource"

grails dbm-update --stacktrace
verifyExitCode $? "dbm-update"

grails dbm-update --stacktrace --dataSource=reports
verifyExitCode $? "dbm-update for reports datasource"

# insert initial data
grails run-script scripts/PopulateData.groovy --stacktrace
verifyExitCode $? "populate-data"

# fix Order.customer by making it a domain class
cd -
cp Customer.groovy "$APP_DIR/grails-app/domain/migrationtests/"
cp Order.v2.groovy "$APP_DIR/grails-app/domain/migrationtests/Order.groovy"
cp customer.changelog.groovy "$APP_DIR/grails-app/migrations"
cd -

grails dbm-register-changelog customer.changelog.groovy --stacktrace
verifyExitCode $? "dbm-register-changelog"
grails dbm-update --stacktrace
verifyExitCode $? "dbm-update"

# fix Product.prize -> Product.price
cd -
cp Product.v2.groovy "$APP_DIR/grails-app/domain/migrationtests/Product.groovy"
cp price.changelog.groovy "$APP_DIR/grails-app/migrations"
cd -

grails dbm-register-changelog price.changelog.groovy --stacktrace
verifyExitCode $? "dbm-register-changelog"
grails dbm-update --stacktrace
verifyExitCode $? "dbm-update"

#verify data after migrations
grails run-script scripts/VerifyData.groovy --stacktrace
verifyExitCode $? "verify-data"

echo "SUCCESS!"