UPDATE GRAPH_TABLE(neo4j
    MERGE (f)
    SET f."name" = "nodename", f."key" = "key", f.note = "note", f.ts = "ts", f:TestNode
)