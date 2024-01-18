-- Create Data
UPDATE GRAPH_TABLE(neo4j
    CREATE (m)-[r]->(n)
    SET m IS TestNode,
        r IS DEP,
        n IS TestNode, n.key = "testKey", n.property = "testProperty"
);

INSERT INTO KVS
VALUES ("testKey", "testValue");

-- Select Data From Both and Join
SELECT kvt.key AS key, kvt.value AS value, gt.n_property AS property
FROM (
    SELECT * FROM KVS
    WHERE key = "testKey"
) AS kvt
INNER JOIN (
    SELECT * FROM GRAPH_TABLE(neo4j
        MATCH (m IS TestNode)-[r IS DEP]->(n)
        COLUMNS (
            n.key AS n_key,
            n.property AS n_property
        )
    )
) AS gt
ON kvt.key = gt.n_key;

-- Cleanup
UPDATE GRAPH_TABLE(neo4j
    MATCH (m IS TestNode)-[r IS DEP]->(n IS TestNode)
    DETACH DELETE m, r, n
);

DELETE FROM KVS
WHERE key = "testKey";