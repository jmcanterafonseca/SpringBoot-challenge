package org.weather.util;

import org.weather.models.WeatherForecastAvg;

import java.util.Date;
import java.util.Hashtable;

/**
 * A cache that holds average WeatherForecasts indexed by city
 */
public class ForecastAvgCache {
    private long timeout;

    /**
     * After timeout has elapsed the entry is considered as invalid
     *
     * @param timeout (in seconds)
     */
    public ForecastAvgCache(long timeout) {
        this.timeout = timeout * 1000;
    }

    private class CacheEntry {
        public Date timestamp;
        public WeatherForecastAvg forecast;
        public String city;

        public CacheEntry(String city, WeatherForecastAvg forecast, Date timestamp) {
            this.forecast = forecast;
            this.timestamp = timestamp;
            this.city = city;
        }
    }

    private Hashtable<String, CacheEntry> entries = new Hashtable<>();

    public WeatherForecastAvg get(String city) {
        WeatherForecastAvg out = null;

        CacheEntry entry = entries.get(city);
        if (entry != null) {
            long now = new Date().getTime();

            if (now > (entry.timestamp.getTime() + timeout)) {
                entries.remove(city);
            } else {
                out = entry.forecast;
            }
        }
        return out;
    }

    public void put(String city, WeatherForecastAvg forecast) {
        CacheEntry entry = new CacheEntry(city, forecast, new Date());

        entries.put(city, entry);
    }
}
