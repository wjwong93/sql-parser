UPDATE GRAPH_TABLE(neo4j
    CREATE (n)
    SET n IS TestNode, n.property = "testProperty", n.delete_me = "delete_this_property"
);

SELECT *
FROM GRAPH_TABLE(neo4j
    MATCH (n IS TestNode)
    WHERE n.property = "testProperty"
    COLUMNS (n.property AS n_property, n.delete_me AS n_delete_me)
);

UPDATE GRAPH_TABLE(neo4j
    MATCH (n IS TestNode)
    REMOVE n.delete_me
);

UPDATE GRAPH_TABLE(neo4j
    MATCH (n)
    WHERE n.property = "testProperty"
    REMOVE n IS TestNode
);

UPDATE GRAPH_TABLE(neo4j
    MATCH (n)
    WHERE n.property = "testProperty"
    DETACH DELETE n
);