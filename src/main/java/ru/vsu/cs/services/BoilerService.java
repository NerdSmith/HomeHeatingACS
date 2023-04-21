package ru.vsu.cs.services;

import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.mappers.BoilerMapper;
import ru.vsu.cs.mappers.Mapper;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.dtos.BoilerDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoilerService extends CrudService<BoilerDto, Boiler> {

    public BoilerService(Dao<Boiler> repository, Mapper<Boiler, BoilerDto> mapper) {
        super(repository, mapper);
    }

    public List<BoilerDto> getAll() {
        return repository.getAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    private Boiler findById(int id) {
        Optional<Boiler> b = repository.get(id);
        return b.orElse(null);
    }

    public BoilerDto getById(int id) {
        Boiler b = findById(id);
        if (b != null) {
            return mapper.toDTO(b);
        }
        return null;
    }

    public void deleteById(int id) {
        Boiler b = findById(id);
        if (b != null) {
            repository.delete(b);
        }
    }

    public void saveNew(BoilerDto boilerDto) {
        repository.save(mapper.toEntity(boilerDto));
    }

    public void update(int id, BoilerDto boilerDto) {
        Boiler oldBoiler = findById(id);
        if (oldBoiler != null) {
            oldBoiler.setBoilerState(mapper.toEntity(boilerDto).getBoilerState());
            repository.update(oldBoiler);
        }
    }


}
