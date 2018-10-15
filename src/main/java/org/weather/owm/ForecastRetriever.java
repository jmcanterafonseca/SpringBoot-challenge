package org.weather.owm;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.weather.errors.CityNotFoundError;
import org.weather.errors.ForecastServiceError;
import org.weather.errors.NetworkError;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
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

    private static String BASE_OWM_API = "http://api.openweathermap.org/data/2.5/forecast?q={0}&units=metric&APPID={1}";

    /**
     * Retrieves a weather forecast for the city passed as parameter
     * Uses Open Weather Map
     *
     * @param city
     * @return
     * @throws ForecastServiceError
     */
    public Map<String, Object> retrieve(String city) throws ForecastServiceError, CityNotFoundError,NetworkError {
        String openWeatherMapUri = MessageFormat.format(BASE_OWM_API,
                new String[]{URLEncoder.encode(city),"19ef5669b666490450fae9f6606c4f97"});

        HttpGet getRequest = new HttpGet(openWeatherMapUri);

        HttpClient httpClient;
        HttpResponse result;
        // send the GET request
        try {
            httpClient = HttpClientBuilder.create().build();
            result = httpClient.execute(getRequest);
        } catch (Throwable thr) {
            throw new NetworkError("Error while getting forecast through Open Weather Map: " + thr.getMessage());
        }
        int statusCode = result.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            String forecastData;
            try {
                forecastData = EntityUtils.toString(result.getEntity(), "UTF-8");
            }
            catch(IOException ioe) {
                throw new NetworkError(ioe.getMessage());
            }
            JsonParser parser = JsonParserFactory.getJsonParser();
            return parser.parseMap(forecastData);
        } else if (statusCode == 404) {
            throw new CityNotFoundError("City not found: " + city);
        } else {
            throw new ForecastServiceError(statusCode, "Open Weather Map Error");
        }
    }
}

