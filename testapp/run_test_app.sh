#!/bin/bash

# Creates a test app and installs the plugin, then changes domain classes and does the required
# migrations. Change the hard-coded values in the variables below for your local system to use.
# Create a MySQL database 'migrationtest' and drop/create before each run.

PLUGIN_DIR="/home/burt/workspace/grails/plugins/grails-database-migration"
TESTAPP_DIR="/home/burt/workspace/testapps/migration"
HOME_DIR="/home/burt"
APP_NAME="migrationtests"
DB_NAME="migrationtest"
PLUGIN_VERSION="1.0"

#GRAILS_VERSION="1.3.3"
#GRAILS_HOME="/home/burt/dev/javalib/grails-$GRAILS_VERSION"

GRAILS_VERSION="2.0.0.BUILD-SNAPSHOT"
GRAILS_HOME="/home/burt/workspace.grails"

PATH=$GRAILS_HOME/bin:$PATH

APP_DIR="$TESTAPP_DIR/$APP_NAME"

verifyExitCode() {
	if [ $1 -ne 0 ]; then
		echo "ERROR: $2 failed with exit code $1"
		exit $1
	fi
}


mkdir -p $TESTAPP_DIR
cd $TESTAPP_DIR

rm -rf "$APP_NAME"
rm -rf "$HOME_DIR/.grails/$GRAILS_VERSION/projects/$APP_NAME"
grails create-app "$APP_NAME" --stacktrace
verifyExitCode $? "create-app"

cd "$PLUGIN_DIR/testapp"

# initial domain classes
mkdir "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME"
cp Product.v1.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/Product.groovy"
cp Order.v1.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/Order.groovy"
cp OrderItem.v1.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/OrderItem.groovy"

# config
cp BuildConfig.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/conf"
cp Config.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/conf"
cp DataSource.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/conf"

# scripts
cp PopulateData.groovy "$TESTAPP_DIR/$APP_NAME/scripts/"
cp VerifyData.groovy "$TESTAPP_DIR/$APP_NAME/scripts/"

# drop and create db
mysql -u "$DB_NAME" -p"$DB_NAME" -D "$DB_NAME" -e "drop database if exists $DB_NAME; create database $DB_NAME"
verifyExitCode $? "drop/create database"

cd $APP_DIR

grails compile --stacktrace

# install plugin

grails install-plugin hibernate $GRAILS_VERSION --force --stacktrace

#2.0 hack
cp "$PLUGIN_DIR/grails-database-migration-$PLUGIN_VERSION.zip" "$TESTAPP_DIR/$APP_NAME/lib/database-migration-$PLUGIN_VERSION.zip"

grails install-plugin "$PLUGIN_DIR/grails-database-migration-$PLUGIN_VERSION.zip" --stacktrace
verifyExitCode $? "install-plugin"

grails compile --stacktrace

# create the initial changelog and export to db
grails dbm-create-changelog --stacktrace
verifyExitCode $? "dbm-create-changelog"

grails dbm-generate-gorm-changelog initial.groovy --add --stacktrace
verifyExitCode $? "dbm-generate-gorm-changelog"

grails dbm-update --stacktrace
verifyExitCode $? "dbm-update"

# insert initial data
grails populate-data --stacktrace
verifyExitCode $? "populate-data"

# fix Order.customer by making it a domain class
cd -
cp Customer.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/"
cp Order.v2.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/Order.groovy"
cp customer.changelog.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/migrations"
cd -

grails dbm-register-changelog customer.changelog.groovy --stacktrace
verifyExitCode $? "dbm-register-changelog"
grails dbm-update --stacktrace
verifyExitCode $? "dbm-update"

# fix Product.prize -> Product.price
cd -
cp Product.v2.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/Product.groovy"
cp price.changelog.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/migrations"
cd -

grails dbm-register-changelog price.changelog.groovy --stacktrace
verifyExitCode $? "dbm-register-changelog"
grails dbm-update --stacktrace
verifyExitCode $? "dbm-update"

# verify data after migrations
grails verify-data --stacktrace
verifyExitCode $? "verify-data"

echo "SUCCESS!"

