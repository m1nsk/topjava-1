curl -X GET http://localhost:8080/topjava/rest/meals/filter/date?start_d=2015-05-30&start_t=07%3A00&end_d=2015-05-31&end_t=11%3A00 HTTP/1.1
curl -X GET http://localhost:8080/topjava/rest/meals HTTP/1.1
curl -X POST http://localhost:8080/topjava/rest/meals HTTP/1.1 -d "{\"id\":null,\"dateTime\":\"2015-06-01T18:00:00\",\"description\":\"Созданный ужин\",\"calories\":300,\"user\":null}" -H "Content-Type: application/json"
curl -X GET http://localhost:8080/topjava/rest/meals/100002 HTTP/1.1
curl -X PUT http://localhost:8080/topjava/rest/meals/100002 HTTP/1.1 -d "{\"id\":100002,\"dateTime\":\"2015-06-01T18:00:00\",\"description\":\"Созданный ужин\",\"calories\":300,\"user\":null}" -H "Content-Type: application/json"
curl -X DELETE http://localhost:8080/topjava/rest/meals/100002