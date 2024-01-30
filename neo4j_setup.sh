#!/bin/bash
echo "Creating container..."
docker run \
  -d \
  -p 7474:7474 \
  -p 7687:7687 \
  --env NEO4J_AUTH=none \
  --name sqlpgq \
  neo4j:5.15.0

echo -n "Waiting for Neo4j to start..."
while ! [[ ("$(docker exec sqlpgq neo4j status 2> /dev/null)" =~ "Neo4j is running at pid") ]]; do
  echo -n "."
  sleep 10
done
echo

data_path="src/test/resources/data/yasuda_data.cypher"

if [ "$1" == "oracle" ]; then
  data_path="src/test/resources/data/oracle_data.cypher"
fi

echo "Importing data..."
docker exec -i sqlpgq cypher-shell < "$data_path"

echo "Done."