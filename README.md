# Not ready yet. In progress


### Build and run
> sbt run

### Then check
[http://localhost:8080/events](http://localhost:8080/events)

Also you can open index.html in the root of project

### Main problems
1. I'm in process of implementing streaming parsing. Currently I parse response slower then 5 seconds. And I have to have new date each 5 seconds. So seems I have a problem :)
2. Also my front-end page some times doesn't respond because of huge amount of data in response.
3. Memory licks because of accumulation futures for parsing (result of first problem).
3. I have to implement logic of computation for assigning the aircraft to the city (using given to me utils)
4. Tests.. of course tests :) I didn't have enough time...( 
5. Also I would like to implement separate actor for updating.. currently I use server-sent-events scheduler in `com.dexter.dashboard.route.MainRouter`