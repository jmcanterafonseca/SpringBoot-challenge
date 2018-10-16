# SpringBoot_Experiments
Spring Boot Experiments - REST API which Wraps OpenWeather API

## API

The API Supported by this service is https://app.swaggerhub.com/apis-docs/jmcanterafonseca/Wrapped_Weather_API/1.0

## How to run

Assuming maven is installed on your system

```
git clone https://github.com/jmcanterafonseca/SpringBoot_Experiments
mvn spring-boot:run
```

## How to test

```
mvn test
```

# How to run with docker

```
mvn docker:build
docker run -p 8080:8080 jmcanterafonseca/weather-api 
```

## Main challenges faced

* First time developing with Spring Boot. So many annotations are challenging!.
* Average Nightly and Average Daily can be tricky as they depend on the city's timezone. OpenWeatherMap only provides data
referenced to UTC so it is needed to do the corresponding hour conversion. That is the main difficulty of the exercise. 

## Technical Debt

* Unit tests are needed
* More functional tests are needed (for errors, especially)
* Ideally the tz parameter should not be passed and the service take care of obtaining the timezone corresponding to the city
