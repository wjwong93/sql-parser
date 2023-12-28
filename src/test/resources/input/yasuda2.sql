UPDATE GRAPH_TABLE(neo4j
    MATCH (f1), (f2)
    WHERE f1.name = "fromnodename" AND f2.name = "tonodename"
    MERGE (f1)-[ IS RELATION]->(f2)
)