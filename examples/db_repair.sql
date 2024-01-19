-- 1. Identify the affected area of the error
-- Get list of nodes that are dependent on affected node
SELECT DISTINCT *
FROM GRAPH_TABLE(neo4j
    MATCH
        p = (s)-[ IS DEP ]->+(n)
    WHERE s.key =~ ".*202311071200-sensor-gh1-temp-sen11.*"
        AND n.note = "TRUE"
    COLUMNS (
        PATH_LENGTH(p) as path_length, n.key AS n_key
    )
)
ORDER BY path_length;

-- 2. Get the raw data(& function detail) from key value store for recalculation
-- 2.1 Get list of nodes needed for recalculation
--     Execute this query once for each key obtained in Step 1
SELECT DISTINCT *
FROM GRAPH_TABLE(neo4j
    MATCH
        (m)-[ IS DEP ]->(n)
    WHERE n.key = "202311071200-estproduction-gh1-1110,202311071200-estproduction-gh1-1111,202311071200-estproduction-gh1-1112"
        AND m.note = "TRUE" AND n.note = "TRUE"
    COLUMNS (
        m.key AS m_key,
        m.ts AS m_ts
    )
);

-- 2.2 Obtain values from LevelDB
--     Execute this query once for each key obtained in Step 2.1
SELECT * FROM KVS
WHERE key = "202311071200-estproduction-gh1-1110_202311071200"
;

-- Use this query to obtain latest value
--SELECT *
--FROM (
--    SELECT * FROM KVS
--)
--WHERE key LIKE "202311101200-precal-gh1-m2%"
--ORDER BY key DESC
--LIMIT 1
--;

-- 3. Recalculate the affected data using function details and correct data
-- Update in ascending order of path_length from Step 1.
-- This will update in a breadth-first search manner starting from the affected node.

-- 4. Update node’s note of affected data
UPDATE GRAPH_TABLE(neo4j
    MATCH (s)-[ r IS DEP ]->+(n)
    WHERE s.key =~ ".*202311101200-sensor-gh1-temp-sen11.*"
    SET s.note = "FALSE", n.note = "FALSE"
);

-- 5. Create new(correct) nodes and relations from recalculation result
-- Run this query for each new node
UPDATE GRAPH_TABLE(neo4j
    CREATE (n)
    SET
        n IS Estproduction,
        n.name = "estpro1107_202401191200",
        n.key = "202311071200-estproduction-gh1-1110,202311071200-estproduction-gh1-1111,202311071200-estproduction-gh1-1112",
        n.note = "TRUE",
        n.ts = "202401191200"
);

-- Run this query for each new relationship
UPDATE GRAPH_TABLE(neo4j
    MATCH (m), (n)
    WHERE m.key = "202311071200-sensor-gh1-temp-sen11,202311071200-sensor-gh1-temp-sen12,202311071200-sensor-gh1-humid-sen13,202311071200-sensor-gh1-humid-sen14"
    AND m.note = "TRUE"
    AND n.key = "202311071200-estproduction-gh1-1110,202311071200-estproduction-gh1-1111,202311071200-estproduction-gh1-1112"
    AND n.note = "TRUE"
    MERGE (m)-[ IS DEP ]->(n)
);

-- 6. Update keys and values in key value store
INSERT INTO KVS
VALUES ("202311071200-estproduction-gh1-1110_202401191200", "{count:15,weight:1800}");