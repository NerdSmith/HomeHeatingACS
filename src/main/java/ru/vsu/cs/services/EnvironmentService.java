package ru.vsu.cs.services;

import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.daos.DaoCsv.BoilerDao;
import ru.vsu.cs.daos.DaoCsv.EnvironmentDao;
import ru.vsu.cs.daos.DaoCsv.EpochTimerDao;
import ru.vsu.cs.daos.DaoCsv.RoomDao;
import ru.vsu.cs.mappers.EnvironmentMapper;
import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.Environment;
import ru.vsu.cs.models.dtos.EnvironmentDto;
import ru.vsu.cs.utils.IdGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnvironmentService {
    @Autowired
    private static EnvironmentDao repository;
    @Autowired
    private static BoilerDao boilerRep;
    @Autowired
    private static RoomDao roomRep;
    @Autowired
    private static EpochTimerDao epochTimerRep;
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
        Environment i = mapper.toEntity(environmentDto);
        if (boilerRep.get(environmentDto.getBoiler()).isPresent()) {
            i.setBoiler(boilerRep.get(environmentDto.getBoiler()).get());
        }
        if (epochTimerRep.get(environmentDto.getEpochTimer()).isPresent()) {
            i.setEpochTimer(epochTimerRep.get(environmentDto.getEpochTimer()).get());
        }
        for (var r : environmentDto.getRooms()) {
            if (roomRep.get(r).isPresent()) {
                i.addRoom(roomRep.get(r).get());
            }
        }
        i.setId(IdGenerator.getInstance().createID());
        repository.save(i);
    }

//    public void update(int id, EpochTimerDto epochTimerDto) {
//        EpochTimer epochTimer = findById(id);
//        if (epochTimer != null) {
//            epochTimer.setCurrTime(mapper.toEntity(epochTimerDto).getCurrTime());
//            repository.update(epochTimer);
//        }
//    }
}
