#!/bin/sh
pkill -f 'java -jar'
rm productivity.jar
mv productivity-0.0.1-SNAPSHOT.jar productivity.jar
sh restart-productivity.sh