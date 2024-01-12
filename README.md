# Extended SQL/PGL Parser

---

This project aims to realise a Polystore DBMS which supports Graph databases (Neo4j) and Key/Value databases (LevelDB). 
The query language is based on the SQL/PGQ extension to the SQL Standard. 

## Using the middleware
### Load test data into databases
```bash
# Setup LevelDB
python3 convert_script.py

# Setup Neo4j
./neo4j_setup
```

### Parse query and display extracted query fragments
```bash
./gradlew parse --args="path/to/query.sql"
```

### Execute query
```bash
./gradlew run --args="path/to/query.sql"
```

## For development

### Setup ANTLRv4 alias
```bash
./init
```

### Run tests
```bash
./gradlew test
```

### Generate class files from ANTLR grammar
```bash
./gradlew generateGrammarSource
```

### Parse query using ANTLR
Installation of `ANTLRv4` and set up of `grun` alias is required.
```bash
cd build/classes/java/main
grun PostgreSQL root -tokens path/to/query.sql
```