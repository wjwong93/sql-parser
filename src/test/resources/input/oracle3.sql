SELECT gt.cid, gt.name, gt.city, gt.amount
FROM GRAPH_TABLE(aml
    MATCH
        (c1 IS Customer )-[ IS OWNS ]->
            ( IS Account )-[ t1 IS TRANSFERS ]->
            ( IS Account )-[ IS TRANSFERS ]->
            ( IS Account )-[ t2 IS TRANSFERS ]->
            ( IS Account )<-[ IS OWNS ]-(c2 IS Customer )
    WHERE c1.cid = 100
        AND t1.amount > 10000
        AND t2.amount > 10000
    COLUMNS(
        c2.cid,
        c2.name,
        c2.city,
        t1.amount AS amount1,
        t2.amount AS amount2
    )
) AS gt;