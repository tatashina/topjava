####getAll():
> curl "localhost:8080/topjava/rest/meals"

####get():
> curl "localhost:8080/topjava/rest/meals/100002"

####delete():
> curl -X DELETE "localhost:8080/topjava/rest/meals/100003" 

####update():
> curl -X PUT -d '{"id":100004,"dateTime":"2015-05-30T20:00:00","description":"UPDATED","calories":500}' -H 'Content-Type:application/json' "localhost:8080/topjava/rest/meals/100004"

####create():
> curl -d '{"dateTime":"2019-11-25T14:00:00","description":"New dinner","calories":1000}' -H 'Content-Type:application/json' "localhost:8080/topjava/rest/meals"

####getBetween():
> curl "localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&startTime=12:00&endDate=2015-05-30&endTime=21:00"