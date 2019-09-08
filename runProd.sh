#!/usr/bin/env bash

read_input() {
    local input=""
    if [[ "$2" == "no_crypt" ]] ; then
	    read -p "$1" input
    else
	    read -s -p "$1" input
	    echo $"" > /dev/stderr
    fi;
    echo ${input}
}

validate_input() {
    local input=$(read_input "$1" "$2")
    check_input=${#input}
    while [[ check_input -le 0 ]]; do
        echo "Write $1" > /dev/stderr
        input=$(read_input "$1" "$2")
	    check_input=${#input}
    done
    echo ${input}
}

database_username=$(validate_input "database username: " "no_crypt")
database_pass=$(validate_input "database password: ")
email_pass=$(validate_input "email password: ")
resend_pass=$(validate_input "resend password: ")

mvn clean install:install-file -Durl=file:./lib/ -DrepositoryId=lib -DupdateReleaseInfo=true \
-Dfile=./lib/crawler-1.0.jar -DgroupId=com.mzweigert.crawler -DartifactId=crawler -Dversion=1.0 -DpomFile=./lib/crawler-1.0.pom.xml
mvn install -DskipTests=true
nohup java -jar -Dspring.profiles.active=prod target/job-notifier.jar \
--spring.datasource.username="${database_username}" --spring.datasource.password="${database_pass}" \
--email.password="${email_pass}" --resend.password="${resend_pass}" &