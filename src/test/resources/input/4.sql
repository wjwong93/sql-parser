SELECT *
FROM GRAPH_TABLE(T1
    MATCH
        (s)-[r IS dep]->+(n)
    WHERE s.key =~ '.*{key}.*'
        AND n.note = true
    COLUMNS(s, n)
)

SELECT *
FROM GRAPH_TABLE(T2
    MATCH
        (m)-[]->{0,1}(n)-[]->{0,1}(l)
    WHERE m.note = true
        AND l.note = true
    COLUMNS(m, l)
)