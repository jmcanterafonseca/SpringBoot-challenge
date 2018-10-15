package org.weather.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.weather.ForecastAvgCalculator;
import org.weather.errors.*;
import org.weather.models.WeatherForecastAvg;
import org.weather.owm.ForecastRetriever;

import java.util.Map;

@RestController
public class WeatherAPIController {

    private ForecastAvgCalculator calculator = ForecastAvgCalculator.getInstance();

    @RequestMapping("/data/v1/WeatherForecasts/average")
    public WeatherForecastAvg getWeatherForecastAvg(@RequestParam(value = "city") String city,
                                                    @RequestParam(value = "tz", required = false) Integer tzOffset)
            throws ApiError {

        // In the absence of timezone UTC it is assumed
        if (tzOffset == null) {
            tzOffset = 0;
        }

        WeatherForecastAvg forecast = calculator.calculate(city, tzOffset);

        return forecast;
    }
}
