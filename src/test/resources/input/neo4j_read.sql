SELECT gt.s_name FROM GRAPH_TABLE(neo4j
    MATCH
        (s)-[r IS DEP]->+(n)
    WHERE s.name =~ ".*estpro1110_202311101200.*"
        AND (n.note = "TRUE" OR n.note = "simulation")
    COLUMNS(s.name AS s_name, n.name AS n_name)
) AS gt
WHERE n_name = "fruit1111_202311131500"
;