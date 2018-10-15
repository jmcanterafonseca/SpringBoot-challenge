package org.weather;

import org.weather.util.AverageHolder;
import org.weather.util.DataUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Calculates the average weather forecast for the next 3 days
 *
 */
public class ForecastAvgCalculator {
    private static ForecastAvgCalculator ourInstance = new ForecastAvgCalculator();

    private ForecastAvgCalculator() {
    }

    public static ForecastAvgCalculator getInstance() {
        return ourInstance;
    }

    /**
     * Calculates the average Weather Forecast using the Open Weather Map data
     *
     * @param openWeatherData
     * @return WeatherForecastAvg object with all the parameters calculated
     */
    public WeatherForecastAvg calculate(Map<String, Object> openWeatherData, int tzOffset) {
        Date now = new java.util.Date();

        TimeZone cityTimezone = TimeZone.getTimeZone("GMT" + new DecimalFormat("+#").format(tzOffset));

        Date[] interval = calculateInterval(now, cityTimezone);

        List<Map<String, Object>> forecastEntries = (List<Map<String, Object>>) openWeatherData.get("list");

        AverageHolder temperatureDaily = new AverageHolder();
        AverageHolder temperatureNightly = new AverageHolder();
        AverageHolder pressure = new AverageHolder();

        long lowerInterval = interval[0].getTime();
        long upperInterval = interval[1].getTime();

        for (Map<String, Object> entry : forecastEntries) {
            String timestampText = (String) entry.get("dt_txt");
            long timestamp;

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // The original data is always in UTC
            sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                timestamp = sdf1.parse(timestampText).getTime();
            } catch (ParseException pse) {
                throw new RuntimeException("Date Parsing: " + pse.getMessage());
            }

            if (timestamp >= lowerInterval && timestamp <= upperInterval) {
                Map<String, Object> main = (Map<String, Object>) entry.get("main");

                pressure.accumulate(DataUtil.getDouble(main, "pressure"));

                // It is needed to check the day / night hours as per the city timezone
                GregorianCalendar testCal = new GregorianCalendar();
                testCal.setTimeZone(cityTimezone);
                testCal.setTime(new Date(timestamp));

                double temperature = DataUtil.getDouble(main, "temp");
                int hour = testCal.get(Calendar.HOUR_OF_DAY);

                if (hour >= 6 && hour <= 18) {
                    temperatureDaily.accumulate(temperature);
                } else {
                    temperatureNightly.accumulate(temperature);
                }
            }
        }


        WeatherForecastAvg forecastAvg = new WeatherForecastAvg();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        sdf.setTimeZone(cityTimezone);

        forecastAvg.validFrom = sdf.format(interval[0]);
        forecastAvg.validTo = sdf.format(interval[1]);

        forecastAvg.avgPressure = pressure.calculateAverage();
        forecastAvg.avgTemperatureDaily = temperatureDaily.calculateAverage();
        forecastAvg.avgTemperatureNightly = temperatureNightly.calculateAverage();

        return forecastAvg;
    }

    /**
     * Calculates the interval for which the average weather parameters will be given
     *
     * @param from Today
     * @return an array of two elements (lower bound, upper bound)
     */
    private Date[] calculateInterval(Date from, TimeZone tz) {
        Date[] out = new Date[2];

        Calendar cal = new GregorianCalendar();

        cal.setTimeZone(tz);
        cal.setTime(from);

        int hour = cal.get(Calendar.HOUR_OF_DAY);

        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 6);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        out[0] = cal.getTime();

        cal.add(Calendar.DATE, 3);
        out[1] = cal.getTime();

        return out;
    }
}
