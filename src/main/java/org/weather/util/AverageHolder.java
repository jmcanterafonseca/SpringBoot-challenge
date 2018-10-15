package org.weather.util;

public class AverageHolder {
    private double accumulated = 0.0;
    private int numEntries;

    public double calculateAverage() {
        double value = accumulated / numEntries;

        return Math.round(value * 100.0) / 100.0;
    }

    public void accumulate(double value) {
        numEntries++;
        accumulated += value;
    }
}
