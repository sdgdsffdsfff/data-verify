#!/bin/bash
#
# set enviroment for worker 
JAVA=java

port=8279
       
JAVA_OPTS="-Xms512m -Xmx1536m  -server -XX:PermSize=64M -XX:MaxPermSize=256m"

LOG_DIR=log
LOG_FILE=bot.log

WORKER_OPTS="$WORKER_OPTS -Dlog.dir=$LOG_DIR"
WORKER_OPTS="$WORKER_OPTS -Dlog.file=$LOG_FILE"