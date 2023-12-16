SELECT DISTINCT gt.cid, gt.name, gt.city
FROM GRAPH_TABLE(aml
    MATCH
        (c1 IS Customer)-[ IS OWNS ]->
            ( IS Account )-[ IS TRANSFERS ]-{1,4}
            ( IS Account )<-[ IS OWNS ]-(c2 IS Customer)
    WHERE c1.cid = 500
    COLUMNS(
        c2.cid,
        c2."name",
        c2.city
    )
) AS gt