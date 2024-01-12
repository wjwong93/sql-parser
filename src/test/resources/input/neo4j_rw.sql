UPDATE GRAPH_TABLE(neo4j
    CREATE (n)
    SET n IS TestNode, n.property = "testProperty"
);

SELECT *
FROM GRAPH_TABLE(neo4j
    MATCH (n IS TestNode)
    WHERE n.property = "testProperty"
    COLUMNS (n.property AS n_property)
);