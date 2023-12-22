SELECT *
FROM GRAPH_TABLE(DependentNodes
    MATCH
        (s)-[r IS dep]->+(n)
    WHERE s.key =~ '.*estpro1110_202311101200.*'
        AND n.note = 'true'
    COLUMNS(s, n)
)