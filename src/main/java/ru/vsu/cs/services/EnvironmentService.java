package ru.vsu.cs.services;

import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.daos.BoilerRep;
import ru.vsu.cs.daos.DaoCsv.BoilerDao;
import ru.vsu.cs.daos.DaoCsv.EnvironmentDao;
import ru.vsu.cs.daos.DaoCsv.EpochTimerDao;
import ru.vsu.cs.daos.DaoCsv.RoomDao;
import ru.vsu.cs.daos.EnvironmentRep;
import ru.vsu.cs.daos.EpochTimerRep;
import ru.vsu.cs.daos.RoomRep;
import ru.vsu.cs.mappers.EnvironmentMapper;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.Environment;
import ru.vsu.cs.models.EpochTimer;
import ru.vsu.cs.models.dtos.EnvironmentDto;
import ru.vsu.cs.utils.IdGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnvironmentService {
    @Autowired
    private static EnvironmentRep repository;
    @Autowired
    private static EnvironmentMapper mapper;

    public List<EnvironmentDto> getAll() {
        return repository.getAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    private Environment findById(int id) {
        Optional<Environment> b = repository.get(id);
        return b.orElse(null);
    }

    public EnvironmentDto getById(int id) {
        Environment b = findById(id);
        if (b != null) {
            return mapper.toDTO(b);
        }
        return null;
    }

    public void deleteById(int id) {
        Environment b = findById(id);
        if (b != null) {
            repository.delete(b);
        }
    }

    public int saveNew(EnvironmentDto environmentDto) {
        Environment i = mapper.toEntity(environmentDto);
        i.setId(IdGenerator.getInstance().createID());
        return repository.save(i);
    }

    public void update(int id, EnvironmentDto environmentDto) {
        Environment oldEnv = findById(id);
        if (oldEnv != null) {
            oldEnv.setTemp(mapper.toEntity(environmentDto).getTemp());
            repository.update(oldEnv);
        }
    }
}
