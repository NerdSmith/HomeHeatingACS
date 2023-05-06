package ru.vsu.cs.daos;

import ru.vsu.cs.models.Environment;
import ru.vsu.cs.utils.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnvironmentRep extends GenRep<Environment> {
    public EnvironmentRep() throws SQLException {
        super(Conn.getConn(), Environment.class);
    }

    @Override
    protected Environment mapResultSetToObject(ResultSet result) throws SQLException {
        Environment environment = new Environment(
                result.getInt("id"),
                result.getInt("temp")
        );
        return environment;
    }

    @Override
    protected void mapObjectToStatement(Environment entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getTemp());
    }
}
