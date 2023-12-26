SELECT *
FROM GRAPH_TABLE(DependentNodes
    MATCH
        (s)-[r IS DEP]->+(n)
    WHERE s.name =~ '.*estpro1110_202311101200.*'
        AND (n.note = 'TRUE' OR n.note = 'simulation')
    COLUMNS(s, n)
)