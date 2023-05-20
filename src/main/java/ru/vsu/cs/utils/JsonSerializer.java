package ru.vsu.cs.utils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonSerializer {
    public static <T> T deserialize(String jsonString, Class<T> clazz) {
        Map<String, Object> jsonMap = parseJson(jsonString);
        T object = createObjectFromMap(jsonMap, clazz);
        return object;
    }

    public static Map<String, Object> parseJson(String jsonString) {
        Map<String, Object> jsonMap = new HashMap<>();

        jsonString = jsonString.replaceAll("\\s", "");

        if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
            jsonString = jsonString.substring(1, jsonString.length() - 1);
        }

        String[] keyValuePairs = jsonString.split(",");

        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split(":");

            if (keyValue.length == 2) {
                String key = keyValue[0].replaceAll("\"", "");
                String value = keyValue[1];

                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                    jsonMap.put(key, value);
                } else if (value.equals("true") || value.equals("false")) {
                    jsonMap.put(key, value.equals("true"));
                } else {
                    try {
                        Double doubleValue = Double.parseDouble(value);
                        if (doubleValue % 1 == 0) {
                            jsonMap.put(key, doubleValue.intValue());
                        } else {
                            jsonMap.put(key, doubleValue);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonMap;
    }

    public static <T> T createObjectFromMap(Map<String, Object> map, Class<T> clazz) {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String fieldName = entry.getKey();
                Object fieldValue = entry.getValue();

                java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
            }

            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String objSerialize(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{");

        for (int i = 0; i < fields.length; i++) {
            try {
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
                jsonBuilder.append(serialize(fieldValue));

                if (i < fields.length - 1) {
                    jsonBuilder.append(",");
                }
            }
            catch (Exception e) {
                System.out.println("Unable parse field");
            }
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }

    public static String serialize(Object obj) throws Exception {
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
                listBuilder.append(serialize(listItem));

                if (i < list.size() - 1) {
                    listBuilder.append(",");
                }
            }

            listBuilder.append("]");
            return listBuilder.toString();
        }

        if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            List<Object> list = Arrays.asList(array);
            return serialize(list);
        }

        return objSerialize(obj);
    }
}
