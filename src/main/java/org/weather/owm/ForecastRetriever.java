package org.weather.owm;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.boot.json.JsonSimpleJsonParser;
import org.weather.errors.CityNotFoundError;
import org.weather.errors.ForecastServiceError;

import java.io.IOException;
import java.util.Map;

/**
 * Retrieves a Weather Forecast from Open Weather Map by city
 */
public class ForecastRetriever {
    private static ForecastRetriever ourInstance = new ForecastRetriever();

    private ForecastRetriever() {
    }

    public static ForecastRetriever getInstance() {
        return ourInstance;
    }

    /**
     * Retrieves a weather forecast for the city passed as parameter
     * Uses Open Weather Map
     *
     * @param city
     * @return
     * @throws ForecastServiceError
     */
    public Map<String, Object> retrieve(String city) throws ForecastServiceError, CityNotFoundError {
        String openWeatherMapUri =
                "http://api.openweathermap.org/data/2.5/forecast?units=metric&APPID=19ef5669b666490450fae9f6606c4f97";
        openWeatherMapUri += "&id=524901";

        HttpGet getRequest = new HttpGet(openWeatherMapUri);

        HttpClient httpClient;
        HttpResponse result;
        // send the GET request
        try {
            httpClient = HttpClientBuilder.create().build();
            result = httpClient.execute(getRequest);
        } catch (Throwable thr) {
            throw new ForecastServiceError("Error while getting forecast: " + thr.getMessage());
        }
        int statusCode = result.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            String forecastData;
            try {
                forecastData = EntityUtils.toString(result.getEntity(), "UTF-8");
            }
            catch(IOException ioe) {
                throw new ForecastServiceError(ioe.getMessage());
            }
            JsonParser parser = JsonParserFactory.getJsonParser();
            return parser.parseMap(forecastData);
        } else if (statusCode == 404) {
            throw new CityNotFoundError();
        } else {
            throw new ForecastServiceError("Status Code: " + statusCode);
        }
    }

}

