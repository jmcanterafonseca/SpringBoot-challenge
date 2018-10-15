package org.weather.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.weather.WeatherForecastAvg;

import java.io.IOException;

public class WeatherForecastAvgSerializer extends StdSerializer<WeatherForecastAvg> {
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
        jgen.writeNumberField("weatherForecast", value.id);
        jgen.writeStringField("itemName", value.itemName);
        jgen.writeNumberField("owner", value.owner.id);
        jgen.writeEndObject();
    }
}
