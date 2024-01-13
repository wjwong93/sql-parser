-- Create Data
UPDATE GRAPH_TABLE(neo4j
    CREATE (n)
    SET n IS TestNode, n."key" = "testKey", n.property = "testProperty"
);

INSERT INTO KVS
VALUES ("testKey", "testValue");

-- Select Data
SELECT kvt.key AS key, kvt.value AS value, gt.n_property AS property
FROM (
    SELECT * FROM KVS
    WHERE key = "testKey"
) AS kvt
INNER JOIN (
    SELECT * FROM GRAPH_TABLE(neo4j
        MATCH (n IS TestNode)
        COLUMNS (
            n."key" AS n_key,
            n.property AS n_property
        )
    )
) AS gt
ON kvt.key = gt.n_key;

-- Cleanup
UPDATE GRAPH_TABLE(neo4j
    MATCH (n is TestNode)
    DETACH DELETE n
);

DELETE FROM KVS
WHERE key = "testKey";