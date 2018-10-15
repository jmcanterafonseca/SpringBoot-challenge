package org.weather.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.weather.util.WeatherForecastAvgSerializer;

@JsonSerialize(using = WeatherForecastAvgSerializer.class)
public class WeatherForecastAvg {
    public String validFrom;
    public String validTo;

    public double avgTemperatureDaily;
    public double avgTemperatureNightly;
    public double avgPressure;
}
