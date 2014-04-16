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
	./run_test_app.sh
fi