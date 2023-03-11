package ru.vsu.cs.daos.DaoCsv;

import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.Environment;
import ru.vsu.cs.models.EpochTimer;
import ru.vsu.cs.models.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnvironmentDao extends CSVDao<Environment> implements Dao<Environment> {

    private final String FILE_NAME = "environments.csv";
    private final EpochTimerDao epochTimerDao;
    private final BoilerDao boilerDao;
    private final RoomDao roomDao;

    public EnvironmentDao(
            EpochTimerDao epochTimerDao,
            BoilerDao boilerDao,
            RoomDao roomDao
    ) {
        this.epochTimerDao = epochTimerDao;
        this.boilerDao = boilerDao;
        this.roomDao = roomDao;
    }

    @Override
    protected Environment getFromCSV(String[] fields) {
        List<Room> rooms = new ArrayList<>();
        for (int id : Arrays.stream(fields[4].split(" ")).mapToInt(Integer::parseInt).toArray()) {
            Optional<Room> optRoom = roomDao.get(id);
            optRoom.ifPresent(rooms::add);
        }
        Optional<EpochTimer> epochTimer = epochTimerDao.get(Integer.parseInt(fields[2]));
        if (epochTimer.isEmpty()) {
            return null;
        }
        Optional<Boiler> boiler = boilerDao.get(Integer.parseInt(fields[3]));
        if (boiler.isEmpty()) {
            return null;
        }

        return new Environment(
                Integer.parseInt(fields[0]),
                Integer.parseInt(fields[1]),
                epochTimer.get(),
                boiler.get(),
                rooms
        );
    }

    @Override
    protected String[] toCSV(Environment item) {
        return new String[] {
                "" + item.getId(),
                "" + item.getTemp(),
                "" + item.getEpochTimer().getId(),
                "" + item.getBoiler().getId(),
                item.getRooms().stream().map(i -> String.valueOf(i.getId())).collect(Collectors.joining(" "))
        };
    }

    @Override
    protected String getFileName() {
        return FILE_NAME;
    }
}
