package ru.vsu.cs.utils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JsonSerializer {
    public static String serialize(Object obj){
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = fields[i].get(obj);
            } catch (IllegalAccessException e) {
                System.out.printf("Cant serialize %s", fields[i]);
            }
            String fieldName = fields[i].getName();

            jsonBuilder.append("\"").append(fieldName).append("\"");
            jsonBuilder.append(":");
            jsonBuilder.append(getJsonValue(fieldValue));

            if (i < fields.length - 1) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    private static String getJsonValue(Object obj){
        if (obj == null) {
            return "null";
        }

        if (obj instanceof String) {
            return "\"" + obj.toString() + "\"";
        }

        if (obj instanceof Integer || obj instanceof Long || obj instanceof Float || obj instanceof Double) {
            return obj.toString();
        }

        if (obj instanceof Boolean) {
            return ((Boolean) obj) ? "true" : "false";
        }

        if (obj instanceof Date) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "\"" + dateFormat.format(obj) + "\"";
        }

        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            StringBuilder listBuilder = new StringBuilder();
            listBuilder.append("[");

            for (int i = 0; i < list.size(); i++) {
                Object listItem = list.get(i);
                listBuilder.append(getJsonValue(listItem));

                if (i < list.size() - 1) {
                    listBuilder.append(",");
                }
            }

            listBuilder.append("]");
            return listBuilder.toString();
        }

        return serialize(obj);
    }
}
