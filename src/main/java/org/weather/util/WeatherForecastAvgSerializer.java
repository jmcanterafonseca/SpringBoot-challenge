package org.weather.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.weather.models.WeatherForecastAvg;

import java.io.IOException;

public class WeatherForecastAvgSerializer extends StdSerializer<WeatherForecastAvg> {
    private class Fields {
        public Fields(String validFrom, String validTo, TemperatureData temperature, PressureData pressure) {
            this.validFrom = validFrom;
            this.validTo = validTo;
            this.temperature = temperature;
            this.atmosphericPressure = pressure;
        }

        public String validFrom;
        public String validTo;
        public TemperatureData temperature;
        public PressureData atmosphericPressure;
    }

    private class TemperatureData {
        public double averageNightly;
        public double averageDaily;

        public TemperatureData(double avgDaily, double avgNightly) {
            this.averageDaily = avgDaily;
            this.averageNightly = avgNightly;
        }
    }

    private class PressureData {
        public PressureData(double pressure) {
            this.average = pressure;
        }

        public double average;
    }

    public WeatherForecastAvgSerializer() {
        this(null);
    }

    public WeatherForecastAvgSerializer(Class<WeatherForecastAvg> t) {
        super(t);
    }

    @Override
    public void serialize(
            WeatherForecastAvg value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        jgen.writeStartObject();

        TemperatureData tdata = new TemperatureData(value.avgTemperatureDaily, value.avgTemperatureNightly);
        PressureData pdata = new PressureData(value.avgPressure);

        Fields f = new Fields(value.validFrom, value.validTo, tdata, pdata);

        jgen.writeObjectField("weatherForecast", f);

        jgen.writeEndObject();
    }
}
