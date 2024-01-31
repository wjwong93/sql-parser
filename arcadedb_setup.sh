#!/bin/bash

docker run \
  -d \
  -p 2480:2480 \
  -p 2424:2424 \
  --name arcadedb \
  --env JAVA_OPTS="-Darcadedb.server.rootPassword=password" \
  arcadedata/arcadedb:23.12.2