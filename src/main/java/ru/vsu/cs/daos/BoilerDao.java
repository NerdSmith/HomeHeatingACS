package ru.vsu.cs.daos;

import ru.vsu.cs.models.Boiler;

import java.util.*;
import java.util.stream.Collectors;

public class BoilerDao implements Dao<Boiler> {

    private List<Boiler> boilersList = new ArrayList<>();

    @Override
    public Optional<Boiler> get(int id) {
        return Optional.ofNullable(boilersList.get(id));
    }

    @Override
    public Collection<Boiler> getAll() {
        return boilersList.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public int save(Boiler boiler) {
        boilersList.add(boiler);
        int index = boilersList.size() - 1;
        return index;
    }

    @Override
    public void update(Boiler boiler) {
        boilersList.set(boiler.getId(), boiler);
    }

    @Override
    public void delete(Boiler boiler) {
        boilersList.set(boiler.getId(), null);
    }
}
