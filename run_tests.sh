#!/bin/bash
set -e -x
rm -rf target
grails clean
grails compile
grails maven-install
grails test-app :integration
grails test-app :cli
set +e +x
if [[ "$1" == "--all" ]]; then
	echo "Running mysql testapp"
	cd testapp
	echo "Run with hibernate3"
	./run_test_app.sh
	echo "Run with hibernate4"
	./run_test_app.sh -DhibernatePluginVersion=:hibernate4:4.3.4.2
fi
