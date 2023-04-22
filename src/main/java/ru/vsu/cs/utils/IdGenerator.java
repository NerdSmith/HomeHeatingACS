package ru.vsu.cs.utils;

import java.io.*;
import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static IdGenerator INSTANCE;
    private static final String genFileName = "genStash";
    private AtomicLong idCounter = new AtomicLong();

    public int createID()
    {
        int tmp = (int) idCounter.getAndIncrement();
        stashLast();
        return tmp;
    }

    private IdGenerator() {
        loadLast();
    }

    public static IdGenerator getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new IdGenerator();
        }

        return INSTANCE;
    }

    private void loadLast() {
        File f = new File(genFileName);
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(genFileName))) {
                String line = br.readLine();
                idCounter.set(Long.parseLong(line));
            } catch (Exception e) {
                System.out.println("Cant find gen file");
            }
        }
        else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Cant create gen file");
            }
        }
    }

    private void stashLast() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(genFileName))) {
            bw.write(String.valueOf(idCounter));
        } catch (IOException e) {
            System.out.println("Cant find gen file");
        }
    }
}
