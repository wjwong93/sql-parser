SELECT DISTINCT *
FROM GRAPH_TABLE(neo4j
    MATCH (s)-[r1 IS DEP]->+(n)<-[r2 IS DEP]-{2}(m)
    WHERE s.key =~ ".*202311071200-sensor-gh1-temp-sen11.*"
    AND n.note = "TRUE" and m.note = "TRUE"
    ONE ROW PER STEP (v1, e, v2)
    COLUMNS (ELEMENT_ID(v1), ELEMENT_ID(e), ELEMENT_ID(v2))
);