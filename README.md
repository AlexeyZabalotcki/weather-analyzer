# __Project setup steps__
___
* ```git clone <username>/weather-analyzer.git ```
* go to the folder ```cd 'your project folder'```
* paste project url from the first step
* open the project in your IDE ```File->Open->'your project folder'```

# __To ```run``` application you need:__

* open folder with project in the terminal ```cd 'your project folder'```
* enter ```gradle clean build```
* enter ```docker compose up -d --build```

# __To ```stop``` application you need:__

* enter ```docker-compose down```

---

# __How application works:__

* You can add current weather in Minsk to database via POST ```http://localhost:8080/weather/```  endpoint
* You can get the latest weather update in Minsk from the database via GET ```http://localhost:8080/weather/name/Minsk``` endpoint
* You can get any added weather by ID via GET ```http://localhost:8080/weather/find/{id}``` endpoint
* You can get the average temperature for some period via ```http://localhost:8080/weather/temp``` endpoint: 
    
    Request must be:
    ```
     {
      "from": "YYYY-MM-DD",
      "to": "YYYY-MM-DD"
     }
  ```