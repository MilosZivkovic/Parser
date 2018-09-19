-- Find all ip taht have more than 100 requests between 2018-01-01 13:00:00 and 2017-01-01 14:00:00
SELECT ipAddress
FROM access_log
WHERE accessTime > "2017-01-01 13:00:00" AND accessTime < "2017-01-01 14:00:00"
HAVING COUNT(*) > 100

-- Find all requests made by given ip address
SELECT *
FROM access_log
WHERE ipAddress LIKE "192.168.0.1"