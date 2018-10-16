# SpringBoot_Experiments
Spring Boot Experiments - REST API which Wraps OpenWeather API

## API

The API Supported by this service is https://app.swaggerhub.com/apis-docs/jmcanterafonseca/Wrapped_Weather_API/1.0

## How to run

Assuming maven is installed on your system

```
git clone https://github.com/jmcanterafonseca/SpringBoot_Experiments
mvn spring-boot:run
curl "http://localhost:8080/data/v1/WeatherForecasts/average?city=Valladolid&tz=+2"
```

## How to test

```
mvn test
```

# How to run with docker

```
mvn package dockerfile:build
docker run -p 8080:8080 jmcanterafonseca/weather_api:1.0-SNAPSHOT
```

## Main challenges faced

* First time developing with Spring Boot. So many annotations are sometimes challenging!.
* Average Nightly and Average Daily temperature can be tricky as they depend on the city's timezone. OpenWeatherMap only provides data referenced to UTC, thus it is needed to use the corresponding timezone offset when calculating averages. That is the main "difficulty" of the case of study. By default timezone offset is considered to be 0. 
* Cache expiration policies are set as configurable and equal to 1 hour, as a very conversative value, taking into account the
documentation provided by Open Weather Map. 

## Technical Debt

* Ideally the `tz` parameter should not be passed by the API user, but the service itself should take care of obtaining the timezone corresponding to each city. Should `tz` be a mandatory parameter?
* Unit tests are needed. Need to investigate how to do it with Spring Boot. 
* More functional tests are needed (for testing errors, especially)
* I was unable to handle properly REST framework errors, despite having created https://github.com/jmcanterafonseca/SpringBoot_Experiments/blob/master/src/main/java/org/weather/rest/RestErrorAdvice.java
* Trace Logging System needed to avoid the usage of some `System.out.println`
