rm -rf docs
grails doc --pdf
rm -rf docs/manual/api
rm -rf docs/manual/gapi
grails add-tracking
