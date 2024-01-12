# Extended SQL/PGL Parser

---

This project aims to realise a Polystore DBMS which supports Graph databases (Neo4j) and Key/Value databases (LevelDB). 
The query language is based on the SQL/PGQ extension to the SQL Standard. 

### Setup ANTLRv4
```bash
./init
```

### Load test data into databases
```bash
python3 convert_script.py
./neo4j_setup
cat src/test/resources/data/yasuda_data.cypher | docker exec -i sqlpgq cypher-shell
```

### Run tests
```bash
./gradlew test
```

### Run query
```bash
./gradlew run --args="path/to/query.sql"
```

### Parse query and display extracted query fragments
```bash
./gradlew parse --args="path/to/query.sql"
```

### Parse query using ANTLR
Installation of `ANTLRv4` and set up of `grun` alias is required.
```bash
cd build/classes/java/main
grun PostgreSQL root -tokens path/to/query.sql
```