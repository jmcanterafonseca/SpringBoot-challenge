package org.weather.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.weather.ForecastAvgCalculator;
import org.weather.WeatherForecastAvg;
import org.weather.errors.CityNotFoundError;
import org.weather.errors.ForecastServiceError;
import org.weather.owm.ForecastRetriever;

import java.util.Map;

@RestController
public class WeatherAPIController {
    private ForecastRetriever retriever = ForecastRetriever.getInstance();
    private ForecastAvgCalculator calculator = ForecastAvgCalculator.getInstance();

    @RequestMapping("/data/v1/WeatherForecasts/Average")
    public String getWeatherForecastAvg(@RequestParam(value = "city") String city) throws ForecastServiceError, CityNotFoundError {
        Map<String, Object> owmForecast = retriever.retrieve(city);

        WeatherForecastAvg forecast = calculator.calculate(owmForecast);

        return "Greetings from " + city;
    }
}
