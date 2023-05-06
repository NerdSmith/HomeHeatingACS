package ru.vsu.cs.daos;

import ru.vsu.cs.models.Room;
import ru.vsu.cs.models.ValveState;
import ru.vsu.cs.utils.Conn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomRep extends GenRep<Room> {
    public RoomRep() throws SQLException {
        super(Conn.getConn(), Room.class);
    }

    @Override
    protected Room mapResultSetToObject(ResultSet result) throws SQLException {
        Room room = new Room(
                result.getInt("id"),
                ValveState.from(result.getInt("valvestate")),
                result.getFloat("currtemp"),
                result.getBoolean("infraredsensor"),
                result.getInt("workingtemp"),
                result.getInt("waitingtemp"),
                result.getInt("batterysquare"),
                getForeignKeyFromResultSet(result, "environment")
        );
        return room;
    }

    @Override
    protected void mapObjectToStatement(Room entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getValveState().getValue());
        statement.setFloat(2, entity.getCurrTemp());
        statement.setBoolean(3, entity.isInfraredSensor());
        statement.setInt(4, entity.getWorkingTemp());
        statement.setInt(5, entity.getWaitingTemp());
        statement.setInt(6, entity.getBatterySquare());
        setForeignKeyParameter(statement, 7, entity.getEnvironment());
    }
}
