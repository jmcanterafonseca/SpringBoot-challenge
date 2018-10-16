package org.weather.test;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    HttpHeaders headers = new HttpHeaders();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void averageWeatherForCityTest() throws Exception {
        Map<String, Object> forecast = getWeatherForecast(restTemplate);

        Assert.assertTrue(forecast.get("weatherForecast") != null);
    }

    @Test
    public void averageWeatherForCityTimezoneTest() throws Exception {
        Map<String, Object> forecastData = (Map<String, Object>) getWeatherForecast(restTemplate).get("weatherForecast");

        Assert.assertTrue(((String) forecastData.get("validFrom")).endsWith("2"));
        Assert.assertTrue(((String) forecastData.get("validTo")).endsWith("2"));

        // TODO: Assert that the difference in days is 3 (parsing the ISO8601 date serialization)
    }

    @Test
    public void averageWeatherForCityTemperatureTest() throws Exception {
        Map<String, Object> forecastData = (Map<String, Object>) getWeatherForecast(restTemplate).get("weatherForecast");
        Map<String, Object> temperatureData = (Map<String, Object>) forecastData.get("temperature");

        Assert.assertTrue((Double) temperatureData.get("averageDaily") >=
                (Double) temperatureData.get("averageNightly"));
    }

    @Test
    public void averageWeatherForCityPressureTest() throws Exception {
        Map<String, Object> forecastData = (Map<String, Object>) getWeatherForecast(restTemplate).get("weatherForecast");
        Map<String, Object> pressureData = (Map<String, Object>) forecastData.get("atmosphericPressure");

        // TODO: A more robust check?
        Assert.assertTrue((Double)pressureData.get("average") > 0);
    }

    private Map<String, Object> getWeatherForecast(TestRestTemplate restTemplate) {
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/data/v1/WeatherForecasts/average?city=Valladolid&tz=2"),
                HttpMethod.GET.GET, entity, String.class);

        String data = response.getBody();

        Map<String, Object> forecast = JsonParserFactory.getJsonParser().parseMap(data);

        return forecast;
    }
}
