#!/bin/bash

BASH_PATH=$( readlink -m $( type -p ${0} ))
BASE_DIR=`dirname "${BASH_PATH}"`

mvn clean install

mkdir -p $BASE_DIR/RedisServer-Server/target/plugins
cp -a $BASE_DIR/RedisServer-RedisProviderPlugin/target/RedisServer-RedisProviderPlugin-1.0.0-SNAPSHOT.jar $BASE_DIR/RedisServer-Server/target/plugins
cp -a $BASE_DIR/RedisServer-WriterPlugin/target/RedisServer-WriterPlugin-1.0.0-SNAPSHOT.jar $BASE_DIR/RedisServer-Server/target/plugins
cp -a $BASE_DIR/RedisServer-ReaderPlugin/target/RedisServer-ReaderPlugin-1.0.0-SNAPSHOT.jar $BASE_DIR/RedisServer-Server/target/plugins
java -jar $BASE_DIR/RedisServer-Server/target/RedisServer-Server-1.0.0-SNAPSHOT.jar
