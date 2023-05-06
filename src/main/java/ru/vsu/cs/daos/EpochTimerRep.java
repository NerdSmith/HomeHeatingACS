package ru.vsu.cs.daos;

import ru.vsu.cs.models.EpochTimer;
import ru.vsu.cs.utils.Conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EpochTimerRep extends GenRep<EpochTimer> {
    public EpochTimerRep() throws SQLException {
        super(Conn.getConn(), EpochTimer.class);
    }

    @Override
    protected EpochTimer mapResultSetToObject(ResultSet result) throws SQLException {
        EpochTimer epochTimer = new EpochTimer(
                result.getInt("id"),
                result.getInt("currtime"),
                getForeignKeyFromResultSet(result, "environment")
        );
        return epochTimer;
    }

    @Override
    protected void mapObjectToStatement(EpochTimer entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getCurrTime());
        setForeignKeyParameter(statement, 2, entity.getEnvironment());
    }
}
