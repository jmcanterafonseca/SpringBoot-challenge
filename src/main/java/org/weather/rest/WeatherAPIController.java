package org.weather.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.weather.ForecastAvgCalculator;
import org.weather.errors.*;
import org.weather.models.WeatherForecastAvg;

@RestController
public class WeatherAPIController {

    @Autowired
    private Environment env;

    private ForecastAvgCalculator calculator = ForecastAvgCalculator.getInstance();


    @RequestMapping("/data/v1/WeatherForecasts/average")
    public WeatherForecastAvg getWeatherForecastAvg(@RequestParam(value = "city") String city,
                                                    @RequestParam(value = "tz", required = false) Integer tzOffset)
            throws ApiError {

        // In the absence of timezone UTC it is assumed
        if (tzOffset == null) {
            tzOffset = 0;
        }

        calculator.setConfig(env.getProperty("api.owmKey"), Integer.parseInt(env.getProperty("api.cacheTimeout")));

        WeatherForecastAvg forecast = calculator.calculate(city, tzOffset);

        return forecast;
    }
}
