package ru.vsu.cs.services;

import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.daos.DaoCsv.EpochTimerDao;
import ru.vsu.cs.daos.EpochTimerRep;
import ru.vsu.cs.mappers.EpochTimerMapper;
import ru.vsu.cs.models.Environment;
import ru.vsu.cs.models.EpochTimer;
import ru.vsu.cs.models.dtos.EpochTimerDto;
import ru.vsu.cs.utils.IdGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EpochTimerService {
    @Autowired
    private static EpochTimerRep repository;
    @Autowired
    private static EpochTimerMapper mapper;

    public List<EpochTimerDto> getAll() {
        return repository.getAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    private EpochTimer findById(int id) {
        Optional<EpochTimer> b = repository.get(id);
        return b.orElse(null);
    }

    public EpochTimerDto getById(int id) {
        EpochTimer b = findById(id);
        if (b != null) {
            return mapper.toDTO(b);
        }
        return null;
    }

    public void deleteById(int id) {
        EpochTimer b = findById(id);
        if (b != null) {
            repository.delete(b);
        }
    }

    public int saveNew(EpochTimerDto epochTimerDto) {
        EpochTimer i = mapper.toEntity(epochTimerDto);
        i.setId(IdGenerator.getInstance().createID());
        return repository.save(i);
    }

    public void update(int id, EpochTimerDto epochTimerDto) {
        EpochTimer epochTimer = findById(id);
        if (epochTimer != null) {
            epochTimer.setCurrTime(mapper.toEntity(epochTimerDto).getCurrTime());
            repository.update(epochTimer);
        }
    }
}
