package ru.vsu.cs.services;

import ru.vsu.cs.daos.Dao;
import ru.vsu.cs.mappers.BoilerMapper;
import ru.vsu.cs.mappers.Mapper;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.dtos.BoilerDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class CrudService<Dto, Entity> {
    protected final Dao<Entity> repository;
    protected final Mapper<Entity, Dto> mapper;

    public CrudService(Dao<Entity> repository, Mapper<Entity, Dto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<Dto> getAll() {
        return repository.getAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    private Entity findById(int id) {
        Optional<Entity> entity = repository.get(id);
        return entity.orElse(null);
    }

    public Dto getById(int id) {
        Entity entity = findById(id);
        if (entity != null) {
            return mapper.toDTO(entity);
        }
        return null;
    }

    public void deleteById(int id) {
        Entity entity = findById(id);
        if (entity != null) {
            repository.delete(entity);
        }
    }

    public void saveNew(Dto dto) {
        repository.save(mapper.toEntity(dto));
    }

    public void update(int id, Dto dto) {
        Entity entity = findById(id);
        if (entity != null) {
            System.out.println("Called abs class update\nNone changes applied!");
            repository.update(entity);
        }
    }
}
