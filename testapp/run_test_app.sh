#!/bin/bash

# Creates a test app and installs the plugin, then changes domain classes and does the required
# migrations. Change the hard-coded values in the variables below for your local system to use.

PLUGIN_DIR="/home/burt/workspace/grails/plugins/grails-database-migration"
TESTAPP_DIR="/home/burt/workspace/testapps/migration"
HOME_DIR="/home/burt"
APP_NAME="migrationtests"
DB_NAME="migrationtest"
GRAILS_VERSION="1.3.3"
PLUGIN_VERSION="0.1"

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
grails create-app "$APP_NAME"
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

# install plugin
grails install-plugin "$PLUGIN_DIR/grails-database-migration-$PLUGIN_VERSION.zip"
verifyExitCode $? "install-plugin"

grails compile

# create the initial changelog and export to db
grails dbm-create-changelog
verifyExitCode $? "dbm-create-changelog"

grails dbm-generate-gorm-changelog initial.groovy --add
verifyExitCode $? "dbm-generate-gorm-changelog"

grails dbm-update
verifyExitCode $? "dbm-update"

# insert initial data
grails populate-data
verifyExitCode $? "populate-data"

# fix Order.customer by making it a domain class
cd -
cp Customer.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/"
cp Order.v2.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/Order.groovy"
cp customer.changelog.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/conf/migrations"
cd -

grails dbm-register-changelog customer.changelog.groovy
verifyExitCode $? "dbm-register-changelog"
grails dbm-update
verifyExitCode $? "dbm-update"

# fix Product.prize -> Product.price
cd -
cp Product.v2.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/domain/$APP_NAME/Product.groovy"
cp price.changelog.groovy "$TESTAPP_DIR/$APP_NAME/grails-app/conf/migrations"
cd -

grails dbm-register-changelog price.changelog.groovy
verifyExitCode $? "dbm-register-changelog"
grails dbm-update
verifyExitCode $? "dbm-update"

# verify data after migrations
grails verify-data
verifyExitCode $? "verify-data"

echo "SUCCESS!"

