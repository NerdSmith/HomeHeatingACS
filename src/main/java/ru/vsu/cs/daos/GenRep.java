package ru.vsu.cs.daos;


import ru.vsu.cs.annotations.Column;
import ru.vsu.cs.annotations.ForeignKey;
import ru.vsu.cs.annotations.Id;
import ru.vsu.cs.models.Model;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class GenRep<T> implements Dao<T> {
    private final Connection connection;
    private final Class<T> entityClass;
    private final String tableName;

    public GenRep(Connection connection, Class<T> entityClass) {
        this.connection = connection;
        this.entityClass = entityClass;
        this.tableName = entityClass.getSimpleName().toLowerCase();
    }

    @Override
    public Optional<T> get(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + tableName + " WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return Optional.of(mapResultSetToObject(result));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery("SELECT * FROM " + tableName)) {
            List<T> entities = new ArrayList<>();
            while (result.next()) {
                entities.add(mapResultSetToObject(result));
            }
            return entities;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int save(T entity) {
        String sql = generateInsertSql();
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            mapObjectToStatement(entity, statement);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    mapGeneratedKeysToObject(entity, generatedKeys);
                }
            }
        } catch (SQLException e) {
            return -1;
        }
        return ((Model) entity).getId();
    }

    @Override
    public void update(T entity) {
        String sql = generateUpdateSql();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            mapObjectToStatement(entity, statement);
            statement.setInt(getIdParameterIndex(), getIdFromObject(entity));
            statement.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void delete(T t) {
        try {
            delete(((Model) t).getId());
        }
        catch (SQLException ignored) {
        }
    }

    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    protected abstract T mapResultSetToObject(ResultSet result) throws SQLException;

    protected abstract void mapObjectToStatement(T entity, PreparedStatement statement) throws SQLException;

    protected void mapGeneratedKeysToObject(T entity, ResultSet generatedKeys) throws SQLException {
        ((Model) entity).setId(generatedKeys.getInt(1));
    }

    protected String generateInsertSql() {
        StringBuilder columnsBuilder = new StringBuilder();
        StringBuilder valuesBuilder = new StringBuilder();
        Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(Id.class)) {
                continue;
            }
            if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(ForeignKey.class)) {
                if (columnsBuilder.length() > 0) {
                    columnsBuilder.append(", ");
                    valuesBuilder.append(", ");
                }
                columnsBuilder.append(field.getName());
                valuesBuilder.append("?");
            }
        }
        return "INSERT INTO " + tableName + " (" + columnsBuilder.toString() + ") VALUES (" + valuesBuilder.toString() + ")";
    }

    protected String generateUpdateSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ");
        builder.append(tableName);
        builder.append(" SET ");
        Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(Id.class)) {
                continue;
            }
            if (i > 0) {
                builder.append(", ");
            }
            builder.append(field.getName());
            builder.append(" = ?");
        }
        builder.append(" WHERE id = ?");
        return builder.toString();
    }

    protected int getIdParameterIndex() {
        Field[] fields = entityClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (field.isAnnotationPresent(Id.class)) {
                return i + 1;
            }
        }
        throw new IllegalArgumentException("Entity does not have @Id annotation");
    }

    protected Integer getIdFromObject(T entity) {
        try {
            Field[] fields = entityClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Id.class)) {
                    field.setAccessible(true);
                    return (Integer) field.get(entity);
                }
            }
            throw new IllegalArgumentException("Entity does not have @Id annotation");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Unable to access @Id field in entity", e);
        }
    }

    protected void setForeignKeyParameter(PreparedStatement statement, int parameterIndex, Integer foreignKey) throws SQLException {
        if (foreignKey == null) {
            statement.setNull(parameterIndex, Types.BIGINT);
        } else {
            statement.setInt(parameterIndex, foreignKey);
        }
    }

    protected Integer getForeignKeyFromResultSet(ResultSet result, String columnName) throws SQLException {
        Integer foreignKey = result.getInt(columnName);
        if (result.wasNull()) {
            return null;
        } else {
            return foreignKey;
        }
    }

}
