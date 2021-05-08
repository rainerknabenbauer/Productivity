#!/bin/sh
pkill -f 'java -jar'
java -jar productivity.jar -Dkeystore.location=./ssl/productivity.to.keystore . & disown