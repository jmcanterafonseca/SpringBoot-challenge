package org.weather.util;

import org.weather.models.WeatherForecastAvg;

import java.util.Date;
import java.util.HashMap;

/**
 * A cache that holds average WeatherForecasts indexed by city
 * A timeout policy is implemented to avoid caching forever
 *
 */
public class ForecastAvgCache {
    private long timeout;

    /**
     * After timeout has elapsed the entry is considered as invalid
     *
     * @param timeout (in minutes)
     */
    public ForecastAvgCache(int timeout) {
        setTimeout(timeout);
    }

    public ForecastAvgCache() {

    }

    /**
     *
     *  Timeout is expressed in minutes
     *
     * @param timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout * 60 * 1000;
    }

    private class CacheEntry {
        public Date timestamp;
        public WeatherForecastAvg forecast;
        public String city;
        public boolean outdated = false;

        public CacheEntry(String city, WeatherForecastAvg forecast, Date timestamp) {
            this.forecast = forecast;
            this.timestamp = timestamp;
            this.city = city;
        }
    }

    private HashMap<String, CacheEntry> entries = new HashMap<>();

    public  WeatherForecastAvg get(String city) {
        WeatherForecastAvg out = null;

        CacheEntry entry = entries.get(city);
        if (entry != null && !entry.outdated) {
            long now = new Date().getTime();

            if (now > (entry.timestamp.getTime() + timeout)) {
                entry.outdated = true;
            } else {
                out = entry.forecast;
            }
        }
        return out;
    }

    public  void put(String city, WeatherForecastAvg forecast) {
        CacheEntry entry = new CacheEntry(city, forecast, new Date());

        entries.put(city, entry);
    }
}
