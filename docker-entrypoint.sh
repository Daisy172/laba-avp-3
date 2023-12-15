#!/bin/sh

set JAVA_OPTS=-XX:MaxDirectMemorySize=4g

./gradlew bootRun
