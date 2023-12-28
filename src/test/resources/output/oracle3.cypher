USE aml
MATCH (c1:Customer)-[:OWNS]->(:Account)-[t1:TRANSFERS]->(:Account)-[:TRANSFERS]->(:Account)-[t2:TRANSFERS]->(:Account)<-[:OWNS]-(c2:Customer)
WHERE c1.cid = 100 AND t1.amount > 10000 AND t2.amount > 10000
RETURN c2.cid, c2.name, c2.city, t1.amount AS amount1, t2.amount AS amount2
;