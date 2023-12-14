SELECT *
FROM GRAPH_TABLE(T1
    MATCH
        (s)-[r IS dep]->+(n),
        (m)-[]->{0,1}(n)-[]->{0,1}(l)
    WHERE s.key =~ '.*{key}.*'
        AND n.note = true
        AND m.note = true
        AND l.note = true
    COLUMNS(s, n, m, l)
)