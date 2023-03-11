package ru.vsu.cs.daos.DaoCsv;

import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.BoilerState;
import ru.vsu.cs.models.EpochTimer;

public class EpochTimerDao extends CSVDao<EpochTimer> implements Dao<EpochTimer> {

    private final String FILE_NAME = "epochTimers.csv";

    @Override
    protected EpochTimer getFromCSV(String[] fields) {
        return new EpochTimer(
                    Integer.parseInt(fields[0]),
                    Integer.parseInt(fields[1])
                );
    }

    @Override
    protected String[] toCSV(EpochTimer item) {
        return new String[] {
                "" + item.getId(),
                "" + item.getCurrTime()
        };
    }

    @Override
    protected String getFileName() {
        return FILE_NAME;
    }
}
