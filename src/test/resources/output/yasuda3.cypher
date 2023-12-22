MATCH (s)-[r:dep*]->(n)
WHERE s.key =~ '.*estpro1110_202311101200.*' AND n.note = 'true'
RETURN s, n