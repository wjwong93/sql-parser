SELECT *
FROM GRAPH_TABLE(DependentNodes
    MATCH
        (s)-[r IS dep]->+(n)
    WHERE s.key =~ '.*{key}.*'
        AND n.note = true
    COLUMNS (s, n)
)