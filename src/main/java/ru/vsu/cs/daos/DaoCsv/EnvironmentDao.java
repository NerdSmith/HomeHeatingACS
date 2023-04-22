package ru.vsu.cs.daos.DaoCsv;

import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.Environment;
import ru.vsu.cs.models.EpochTimer;
import ru.vsu.cs.models.Room;

import java.util.*;
import java.util.stream.Collectors;

public class EnvironmentDao extends CSVDao<Environment> implements Dao<Environment> {

    private final String FILE_NAME = "environments.csv";
    @Autowired
    private static EpochTimerDao epochTimerDao;
    @Autowired
    private static BoilerDao boilerDao;
    @Autowired
    private static RoomDao roomDao;


    @Override
    protected Environment getFromCSV(String[] fields) {
        List<Room> rooms = new ArrayList<>();
        Optional<EpochTimer> epochTimer = Optional.empty();
        Optional<Boiler> boiler = Optional.empty();

        if (!Objects.equals(fields[4], "")) {
            for (int id : Arrays.stream(fields[4].split(" ")).mapToInt(Integer::parseInt).toArray()) {
                Optional<Room> optRoom = roomDao.get(id);
                optRoom.ifPresent(rooms::add);
            }
        }
        if (!Objects.equals(fields[2], "")) {
            epochTimer = epochTimerDao.get(Integer.parseInt(fields[2]));
            if (epochTimer.isEmpty()) {
                return null;
            }
        }

        if (!Objects.equals(fields[3], "")) {
            boiler = boilerDao.get(Integer.parseInt(fields[3]));
            if (boiler.isEmpty()) {
                return null;
            }
        }

        return new Environment(
                Integer.parseInt(fields[0]),
                Integer.parseInt(fields[1]),
                epochTimer.orElse(null),
                boiler.orElse(null),
                rooms
        );
    }

    @Override
    protected String[] toCSV(Environment item) {
        return new String[] {
                "" + item.getId(),
                "" + item.getTemp(),
                "" + (item.getEpochTimer() != null ? item.getEpochTimer().getId() : ""),
                "" + (item.getBoiler() != null ? item.getBoiler().getId() : ""),
                item.getRooms().stream().map(i -> String.valueOf(i.getId())).collect(Collectors.joining(" "))
        };
    }

    @Override
    protected String getFileName() {
        return FILE_NAME;
    }
}
