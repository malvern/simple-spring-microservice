#!/usr/bin/env bash
echo 'Running Search Service'
mvn clean install
mavenStatus=$?
if [ $mavenStatus -ne 0 ]
then
  echo 'failed to run mvn clean'
  exit 1
fi
echo 'Done generating Specs and Running unit/Integration test'
cd contact-service || return
check=$?
if [ $check -ne 0 ]
  then
    echo 'failed to move to contact-service directory'
    exit 1
fi
echo 'starting the application'
mvn spring-boot:run
echo 'System is now up and running ...'
