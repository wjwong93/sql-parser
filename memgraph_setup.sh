#!/bin/bash

docker run \
  -d \
  --name memgraph \
  -p 7687:7687 \
  -p 7444:7444 \
  -p 3000:3000 \
  memgraph/memgraph-platform

#docker exec -i memgraph mgconsole < src/test/resources/data/yasuda_data.cypher