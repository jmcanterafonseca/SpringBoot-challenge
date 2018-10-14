package org.weather.util;

import java.util.Map;

public class DataUtil {
    public static double getDouble(Map<String,Object> data,String field) {
        Object value = data.get(field);
        if (value instanceof Double) {
            return (Double)value;
        }
        else if (value instanceof Integer) {
            return new Double((Integer)value);
        }
        else if (value instanceof Long) {
            return new Double((Long)value);
        }
        else {
            throw new RuntimeException("Value cannot be converted to Double");
        }
    }
}
