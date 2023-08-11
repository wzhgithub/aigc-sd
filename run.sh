#!/usr/bin/env sh
set -ex

java -Xmx2g -Xms2g -Xss256k -Dspring.profiles.active=prod -Dfile.encoding=utf-8 -jar /app/aigc-controller-1.0-SNAPSHOT.jar