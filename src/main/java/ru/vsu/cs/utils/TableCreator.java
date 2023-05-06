package ru.vsu.cs.utils;

import ru.vsu.cs.annotations.Column;
import ru.vsu.cs.annotations.ForeignKey;
import ru.vsu.cs.annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator<T> {
    public static <T> void createTable(Connection connection, Class<T> clazz) throws SQLException {
        String tableName = clazz.getSimpleName().toLowerCase();

        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName + " (");

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                sql.append(field.getName().toLowerCase()).append(" SERIAL PRIMARY KEY,");
            } else if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                String name = column.name().isEmpty() ? field.getName().toLowerCase() : column.name().toLowerCase();
                String type = getSqlType(field.getType());
                sql.append(name).append(" ").append(type).append(",");
            }
        }

        sql.deleteCharAt(sql.length() - 1);

        for (Field field : fields) {
            if (field.isAnnotationPresent(ForeignKey.class)) {
                ForeignKey fk = field.getAnnotation(ForeignKey.class);
                String name = fk.name();
                String refTable = fk.refTable();
                String refColumn = fk.refColumn();
                sql.append(", FOREIGN KEY (").append(field.getName().toLowerCase()).append(") REFERENCES ")
                        .append(refTable.toLowerCase()).append("(").append(refColumn.toLowerCase()).append(")");
                sql.append(" ON DELETE ").append(fk.onDelete().toString().replace("_", " "));
            }
        }

        sql.append(");");

        System.out.println(sql.toString());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
            System.out.println("Table " + tableName + " created successfully.");
        }
    }

    private static String getSqlType(Class<?> type) {
        if (type == int.class || type == Integer.class) {
            return "INT";
        } else if (type == long.class || type == Long.class) {
            return "BIGINT";
        } else if (type == double.class || type == Double.class || type == float.class || type == Float.class) {
            return "DOUBLE PRECISION";
        } else if (type == String.class) {
            return "VARCHAR(255)";
        } else if (type == boolean.class || type == Boolean.class) {
            return "BOOLEAN";
        } else if (type.isEnum()) {
            return "INT";
        } else {
            return "INT NOT NULL";
        }
    }
}
