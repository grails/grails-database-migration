#!/bin/bash +xe
HIBERNATE4_PLUGIN=":hibernate4:4.3.5.2"
PREVIOUS_JAVA_OPTS="${JAVA_OPTS}"

runall=0
skip_hibernate4=0
skip_cli=0
# parse options
while getopts ":a:h:c" Option
do
    case $Option in
             a )
                runall=1
             ;;
             h )
                skip_hibernate4=1
             ;;
             c )
                skip_cli=1
             ;;
    esac
done
shift $(($OPTIND - 1))


run_tests() {
    set -e -x
    [ -d target ] && rm -rf target
    ./grailsw clean --non-interactive
    ./grailsw compile --non-interactive
    ./grailsw maven-install --non-interactive
    ./grailsw test-app :integration --non-interactive
    if [[ $skip_cli -ne 1 ]]; then
        ./grailsw test-app :cli --non-interactive
    fi
    set +e +x
}

run_tests
if [[ $skip_hibernate4 -ne 1 ]]; then
    echo "Run with hibernate4" 
    export JAVA_OPTS="-DhibernatePluginVersion=$HIBERNATE4_PLUGIN ${JAVA_OPTS}"
    run_tests
    export JAVA_OPTS="${PREVIOUS_JAVA_OPTS}"
fi

if [[ $runall -eq 1 ]]; then
    echo "Running mysql testapp"
    cd testapp
    echo "Run with hibernate3"
    ./run_test_app.sh
    if [[ $skip_hibernate4 -ne 1 ]]; then
        echo "Run with hibernate4"
        ./run_test_app.sh -DhibernatePluginVersion=$HIBERNATE4_PLUGIN
    fi
fi
