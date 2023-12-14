MATCH (s)-[r:dep*]->(n), (m)-[*0..1]->(n)-[*0..1]->(l)
WHERE s.key =~ '.*{key}.*' AND n.note = true AND m.note = true AND l.note = true
RETURN s, n, m, l