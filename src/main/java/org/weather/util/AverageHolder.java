package org.weather.util;

public class AverageHolder {
    private double accumulated = 0.0;
    private int numEntries;

    public double calculateAverage() {
        return accumulated / numEntries;
    }

    public void accumulate(double value) {
        numEntries++;
        accumulated += value;
    }
}
