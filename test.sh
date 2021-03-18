#!/bin/bash
BASH_PATH=$( readlink -m $( type -p ${0} ))
BASE_DIR=`dirname "${BASH_PATH}"`

mvn clean install

mkdir $BASE_DIR/RedisServer-Server/target/plugins
cp -a $BASE_DIR/RedisServer-Plugin/target/RedisServer-Plugin-1.0.0-SNAPSHOT.jar $BASE_DIR/RedisServer-Server/target/plugins/RedisServer-Plugin-1.0.0-SNAPSHOT.jar
java -jar $BASE_DIR/RedisServer-Server/target/RedisServer-Server-1.0.0-SNAPSHOT.jar
