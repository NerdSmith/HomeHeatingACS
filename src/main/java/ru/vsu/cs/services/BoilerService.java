package ru.vsu.cs.services;

import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.daos.BoilerRep;
import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.daos.DaoCsv.BoilerDao;
import ru.vsu.cs.mappers.BoilerMapper;
import ru.vsu.cs.mappers.Mapper;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.dtos.BoilerDto;
import ru.vsu.cs.utils.IdGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoilerService {
    @Autowired
    private static BoilerRep repository;
    @Autowired
    private static BoilerMapper mapper;

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

    public int saveNew(BoilerDto boilerDto) {
        Boiler i = mapper.toEntity(boilerDto);
        i.setId(IdGenerator.getInstance().createID());
        return repository.save(i);
    }

    public void update(int id, BoilerDto boilerDto) {
        Boiler oldBoiler = findById(id);
        if (oldBoiler != null) {
            oldBoiler.setBoilerState(mapper.toEntity(boilerDto).getBoilerState());
            repository.update(oldBoiler);
        }
    }
}
