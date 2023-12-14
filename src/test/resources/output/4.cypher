MATCH (s)-[r:dep*]->(n)
WHERE s.key =~ '.*{key}.*' AND n.note = true
MATCH (m)-[*0..1]->(n)-[*0..1]->(l) 　
WHERE m.note = true AND l.note = true
RETURN s,n,m,l