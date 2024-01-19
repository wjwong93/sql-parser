SELECT * FROM KVS;

-- Create
INSERT INTO KVS
VALUES ("testKey1", "testValue1"), ("testKey2", "testValue2");

SELECT * FROM KVS;

SELECT *
FROM KVS
WHERE key = "testKey1"
OR key = "testKey2";

-- Update
UPDATE KVS
SET value = "newValue"
WHERE key IN ("testKey1", "testKey2");

SELECT *
FROM KVS
WHERE key IN ("testKey1", "testKey2");

-- Cleanup
DELETE FROM KVS
WHERE key IN ("testKey1", "testKey2");

SELECT * FROM KVS;