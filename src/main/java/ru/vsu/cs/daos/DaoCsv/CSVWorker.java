package ru.vsu.cs.daos.DaoCsv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import ru.vsu.cs.models.Boiler;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class CSVWorker {

    protected List<String[]> readAllFile() {
        List<String[]> res = new ArrayList<>();
        try (
                FileReader fr = new FileReader(getFileName(), StandardCharsets.UTF_8);
                CSVReader reader = new CSVReader(fr)
        ) {
            String[] nextLine;

            while (( nextLine = reader.readNext()) != null) {
                res.add(nextLine);
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }

        return res;
    }

    protected void write(String[] item) {
        List<String[]> res = new ArrayList<>();
        res.add(item);
        writeInner(res, false);
    }

    protected void write(Collection<String[]> items) {
        writeInner(items, false);
    }

    protected void append(Collection<String[]> items) {
        writeInner(items, true);
    }

    protected void append(String[] item) {
        List<String[]> res = new ArrayList<>();
        res.add(item);
        writeInner(res, true);
    }

    protected void writeInner(Collection<String[]> items, boolean append) {
        try (
                FileWriter fw = new FileWriter(getFileName(), append);
                CSVWriter csvWriter = new CSVWriter(fw);
        ) {
            for (String[] line : items) {
                csvWriter.writeNext(line);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    protected abstract String getFileName();
}
