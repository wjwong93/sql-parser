USE MovieRentals
MATCH (cust1:Customer)-[e1]->(movie:Movie)<-[e2]-(cust2:Customer)
WHERE e1.Date_Rented > TO_DATE('2022-02-14') AND movie.genre = 'Romantic Comedy' AND e2.Date_Rented > e1.Date_Rented AND cust1.Last_Name != cust2.Last_Name
RETURN cust1.Last_Name AS EarlierRenter, cust2.Last_Name AS LaterRenter, e1.Date_Rented;