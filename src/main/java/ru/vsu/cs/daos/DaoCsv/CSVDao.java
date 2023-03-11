package ru.vsu.cs.daos.DaoCsv;

import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.BoilerState;

import java.util.*;

public abstract class CSVDao<T> extends CSVWorker implements Dao<T> {
    @Override
    public Optional<T> get(int id) {
        List<String[]> readData = readAllFile();
        String[] found = readData.stream().filter(
                item -> compareCSVId(
                        item, List.of(Integer.toString(id)
                        ).toArray(new String[0])
                )
        ).findAny().orElse(null);
        if (found != null) {
            T fItem = getFromCSV(found);
            return Optional.of(fItem);
        }
        return Optional.empty();
    }

    @Override
    public Collection<T> getAll() {
        List<T> res = new ArrayList<>();

        for (String[] line : readAllFile()) {
            res.add(getFromCSV(line));
        }
        return res;
    }

    @Override
    public int save(T item) {
        List<String[]> readData = readAllFile();
        String[] csvRepr = toCSV(item);
        if (readData.stream().noneMatch(listItem -> compareCSVId(listItem, csvRepr))) {
            append(csvRepr);
            return Integer.parseInt(csvRepr[0]);
        }
        else {
            System.out.println("Exists");
        }
        return -1;
    }

    @Override
    public void update(T t) {
        System.out.println("not implemented");
    }

    @Override
    public void delete(T t) {
        System.out.println("not implemented");
    }

    abstract protected T getFromCSV(String[] fields);

    abstract protected String[] toCSV(T item);

    private boolean compareCSVId(String[] str1, String[] str2) {
        return str1[0].equals(str2[0]);
    }
}
