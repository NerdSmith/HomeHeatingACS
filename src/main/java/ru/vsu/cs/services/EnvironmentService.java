package ru.vsu.cs.services;

import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.daos.DaoCsv.EnvironmentDao;
import ru.vsu.cs.mappers.EnvironmentMapper;
import ru.vsu.cs.models.Environment;
import ru.vsu.cs.models.dtos.EnvironmentDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnvironmentService {
    @Autowired
    private static EnvironmentDao repository;
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

    public void saveNew(EnvironmentDto environmentDto) {
        repository.save(mapper.toEntity(environmentDto));
    }

//    public void update(int id, EpochTimerDto epochTimerDto) {
//        EpochTimer epochTimer = findById(id);
//        if (epochTimer != null) {
//            epochTimer.setCurrTime(mapper.toEntity(epochTimerDto).getCurrTime());
//            repository.update(epochTimer);
//        }
//    }
}
