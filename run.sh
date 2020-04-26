#!/bin/bash
git pull origin master
mvn clean package
PID=$(ps aux|grep blogspider-1.0-SNAPSHOT|grep -v grep|awk '{print $2}')
echo "PID:$PID"
kill -9 "$PID"
nohup java -jar ./target/blogspider-1.0-SNAPSHOT.jar &