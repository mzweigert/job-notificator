#!/usr/bin/env bash

while true; do
    read -p "Do you wish to debug app y - yes/ n - no? : " yn

    case ${yn} in
        [Yy]* )
            echo "Maven running..."
            mvn clean install:install-file -Durl=file:./lib/ -DrepositoryId=lib -DupdateReleaseInfo=true \
            -Dfile=./lib/crawler-1.0.jar -DgroupId=com.mzweigert.crawler -DartifactId=crawler -Dversion=1.0 -DpomFile=./lib/crawler-1.0.pom.xml
            mvn install -DskipTests=true
            port=8080
            echo "Please connect to the port $port"
            nohup java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=${port},suspend=y -jar target/job-notificator.jar &
        break;;
        [Nn]* )
            echo "Maven running..."
            mvn clean install:install-file -Durl=file:./lib/ -DrepositoryId=lib -DupdateReleaseInfo=true \
            -Dfile=./lib/crawler-1.0.jar -DgroupId=com.mzweigert.crawler -DartifactId=crawler -Dversion=1.0 -DpomFile=./lib/crawler-1.0.pom.xml
            mvn install -DskipTests=true
            nohup java -jar target/job-notificator.jar &
        break;;
        * ) echo "Please answer yes or no.";;
    esac
done