package ru.vsu.cs.daos.DaoCsv;

import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.models.EpochTimer;
import ru.vsu.cs.models.Room;
import ru.vsu.cs.models.ValveState;

import java.util.Collection;

public class RoomDao extends CSVDao<Room> implements Dao<Room> {

    private final String FILE_NAME = "rooms.csv";

    @Override
    protected Room getFromCSV(String[] fields) {
        return new Room(
                Integer.parseInt(fields[0]),
                ValveState.from(Integer.parseInt(fields[1])),
                Float.parseFloat(fields[2]),
                Boolean.parseBoolean(fields[3]),
                Integer.parseInt(fields[4]),
                Integer.parseInt(fields[5]),
                Integer.parseInt(fields[6])
        );
    }

    @Override
    protected String[] toCSV(Room item) {
        return new String[] {
                "" + item.getId(),
                "" + item.getValveState().getValue(),
                "" + item.getCurrTemp(),
                "" + item.isInfraredSensor(),
                "" + item.getWorkingTemp(),
                "" + item.getWaitingTemp(),
                "" + item.getBatterySquare()
        };
    }

    @Override
    protected String getFileName() {
        return FILE_NAME;
    }
}
