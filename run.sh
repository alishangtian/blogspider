#!/bin/bash
git pull origin master
mvn clean package
nohup java -jar ./target/blogspider-1.0-SNAPSHOT.jar &