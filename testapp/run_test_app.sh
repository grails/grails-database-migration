#!/bin/bash

# pass all arguments to this script in JAVA_OPTS to grails
export JAVA_OPTS="${@} ${JAVA_OPTS}"

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

../grailsw compile --stacktrace --non-interactive

# create the initial changelog and export to db
../grailsw dbm-create-changelog --stacktrace --non-interactive
verifyExitCode $? "dbm-create-changelog"

# create the initial changelog for reports datasource and export to db
../grailsw dbm-create-changelog --dataSource=reports --stacktrace --non-interactive
verifyExitCode $? "dbm-create-changelog for reports datasource"


../grailsw dbm-generate-gorm-changelog initial.groovy --add --stacktrace --non-interactive
verifyExitCode $? "dbm-generate-gorm-changelog"

../grailsw dbm-generate-gorm-changelog initialReports.groovy --add --stacktrace --dataSource=reports --non-interactive
verifyExitCode $? "dbm-generate-gorm-changelog for reports datasource"

../grailsw dbm-update --stacktrace --non-interactive
verifyExitCode $? "dbm-update"

../grailsw dbm-update --stacktrace --dataSource=reports --non-interactive
verifyExitCode $? "dbm-update for reports datasource"

# insert initial data
../grailsw run-script scripts/PopulateData.groovy --stacktrace --non-interactive
verifyExitCode $? "populate-data"

# fix Order.customer by making it a domain class
cd -
cp Customer.groovy "$APP_DIR/grails-app/domain/migrationtests/"
cp Order.v2.groovy "$APP_DIR/grails-app/domain/migrationtests/Order.groovy"
cp customer.changelog.groovy "$APP_DIR/grails-app/migrations"
cd -

../grailsw dbm-register-changelog customer.changelog.groovy --stacktrace --non-interactive
verifyExitCode $? "dbm-register-changelog"
../grailsw dbm-update --stacktrace --non-interactive
verifyExitCode $? "dbm-update"

# fix Product.prize -> Product.price
cd -
cp Product.v2.groovy "$APP_DIR/grails-app/domain/migrationtests/Product.groovy"
cp price.changelog.groovy "$APP_DIR/grails-app/migrations"
cd -

../grailsw dbm-register-changelog price.changelog.groovy --stacktrace --non-interactive
verifyExitCode $? "dbm-register-changelog"
../grailsw dbm-update --stacktrace --non-interactive
verifyExitCode $? "dbm-update"

#verify data after migrations
../grailsw run-script scripts/VerifyData.groovy --stacktrace --non-interactive
verifyExitCode $? "verify-data"

echo "SUCCESS!"