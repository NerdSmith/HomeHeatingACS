package ru.vsu.cs.daos;

import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.BoilerState;
import ru.vsu.cs.utils.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoilerRep extends GenRep<Boiler> {
    public BoilerRep() throws SQLException {
        super(Conn.getConn(), Boiler.class);
    }

    @Override
    protected Boiler mapResultSetToObject(ResultSet result) throws SQLException {
        Boiler boiler = new Boiler(
                result.getInt("id"),
                BoilerState.from(result.getInt("boilerstate")),
                getForeignKeyFromResultSet(result, "environment")
        );

        return boiler;
    }

    @Override
    protected void mapObjectToStatement(Boiler entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getBoilerState().getValue());
        setForeignKeyParameter(statement, 2, entity.getEnvironment());
    }
}
