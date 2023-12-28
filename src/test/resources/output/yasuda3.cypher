USE neo4j
MATCH (s)-[r:DEP*]->(n)
WHERE s.name =~ '.*estpro1110_202311101200.*' AND (n.note = 'TRUE' OR n.note = 'simulation')
RETURN s, n
;