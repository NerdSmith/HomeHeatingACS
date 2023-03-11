package ru.vsu.cs.daos.DaoCsv;

import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.BoilerState;


public class BoilerDao extends CSVDao<Boiler> implements Dao<Boiler>  {

    private final String FILE_NAME = "boilers.csv";

    @Override
    public String getFileName() {
        return FILE_NAME;
    }

    @Override
    protected Boiler getFromCSV(String[] fields) {
        return new Boiler(
                Integer.parseInt(fields[0]),
                BoilerState.from(Integer.parseInt(fields[1])
                )
        );
    }

    @Override
    protected String[] toCSV(Boiler item) {
        return new String[] {
                "" + item.getId(),
                "" + item.getBoilerState().getValue()
        };
    }
}
