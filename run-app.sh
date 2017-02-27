#!/bin/bash

. /opt/shared/common.sh

cd $(dirname $0)

jar="$PWD/target/CubeTimer-1.0-SNAPSHOT-jar-with-dependencies.jar"
shift

if ! [ -f "$jar" ]
then
  echo "Compiling app before running..." >&2
  mvn clean install || { echo "Error compiling. Make sure maven 2+ installed." >&2; exit 1; }
fi

java -jar "$jar"

