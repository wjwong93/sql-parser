INSERT INTO KVS
VALUES ("testKey1", "testValue1"), ("testKey2", "testValue2");

SELECT *
FROM KVS
WHERE key = "testKey1"
OR key = "testKey2";