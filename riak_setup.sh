#!/bin/bash

docker run \
  -d \
  --name=riak \
  -p 8087:8087 \
  -p 8098:8098 \
  basho/riak-kv:ubuntu-2.2.3

#docker-compose -f riak/docker-compose.yml scale coordinator=1 member=1
#docker-compose -f riak/docker-compose.yml run