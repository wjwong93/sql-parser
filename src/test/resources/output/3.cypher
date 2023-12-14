MATCH (s)-[r:dep*]->(n)
WHERE s.key =~ '.*{key}.*' AND n.note = true
RETURN s, n