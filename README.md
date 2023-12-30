# Extended SQL/PGL Parser

---

This project aims to realise a Polystore DBMS which supports Graph databases (Neo4j) and Key/Value databases (LevelDB). 
The query language is based on the SQL/PGQ extension to the SQL Standard. 

### Load test data into databases
```
python3 convert_script.py
./neo4j_setup
cat src/test/resources/data/yasuda_data.cypher | docker exec -i sqlpgq cypher-shell
```

### Run tests
```
./gradlew test
```

### Extract graph query from SQL/PGQ
```
./gradlew run --args="path/to/query.sql"
```

### Parse SQL query
Installation of `ANTLRv4` and set up of `grun` alias is required.
```
cd build/classes/java/main
grun PostgreSQL root -tokens path/to/query.sql
```