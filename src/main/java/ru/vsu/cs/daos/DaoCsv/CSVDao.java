package ru.vsu.cs.daos.DaoCsv;

import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.BoilerState;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class CSVDao<T> extends CSVWorker implements Dao<T> {

    public CSVDao() {
        File f = new File(getFileName());
        try {
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("err in file create");
        }
    }

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
        String[] csvRepr = toCSV(t);
        List<String[]> readData = readAllFile();

        for (int i = 0; i < readData.size(); i ++) {
            if (compareCSVId(csvRepr, readData.get(i))) {
                readData.set(i, csvRepr);
            }
        }
        write(readData);
    }

    @Override
    public void delete(T t) {
        int idxToDel = -1;
        String[] csvRepr = toCSV(t);
        List<String[]> readData = readAllFile();

        for (int i = 0; i < readData.size(); i ++) {
            if (compareCSVId(csvRepr, readData.get(i))) {
                idxToDel = i;
            }
        }
        if (idxToDel >= 0) {
            readData.remove(idxToDel);
            write(readData);
        }
    }

    abstract protected T getFromCSV(String[] fields);

    abstract protected String[] toCSV(T item);

    private boolean compareCSVId(String[] str1, String[] str2) {
        return str1[0].equals(str2[0]);
    }
}
