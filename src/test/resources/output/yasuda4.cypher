MATCH (s)-[r:DEP*]->(n), (m)-[*0..1]->(n)-[*0..1]->(l)
WHERE s.name =~ '.*estpro1110_202311101200.*'
AND (m.note = 'TRUE' OR m.note = 'simulation')
AND (n.note = 'TRUE' OR n.note = 'simulation')
AND (l.note = 'TRUE' OR l.note = 'simulation')
RETURN s, n, m, l