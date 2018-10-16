package org.weather;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

public class Configuration {
    @Value("${app.owmKey}")
    private String owmKey;
    private long cacheTimeout;

    public Configuration() {

    }

    public void setOwmKey(String key) {
        this.owmKey = key;
    }

    public void setCacheTimeout(long timeout) {
        this.cacheTimeout = timeout;
    }

    public String getOwmKey() {
        return owmKey;
    }

    public long getCacheTimeout() {
        return cacheTimeout;
    }
}
