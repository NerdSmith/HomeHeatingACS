package ru.vsu.cs.services;

import ru.vsu.cs.annotations.Autowired;
import ru.vsu.cs.daos.DaoCsv.RoomDao;
import ru.vsu.cs.daos.RoomRep;
import ru.vsu.cs.mappers.RoomMapper;
import ru.vsu.cs.models.EpochTimer;
import ru.vsu.cs.models.Room;
import ru.vsu.cs.models.dtos.RoomDto;
import ru.vsu.cs.utils.IdGenerator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoomService {
    @Autowired
    private static RoomRep repository;
    @Autowired
    private static RoomMapper mapper;

    public List<RoomDto> getAll() {
        return repository.getAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    private Room findById(int id) {
        Optional<Room> b = repository.get(id);
        return b.orElse(null);
    }

    public RoomDto getById(int id) {
        Room b = findById(id);
        if (b != null) {
            return mapper.toDTO(b);
        }
        return null;
    }

    public void deleteById(int id) {
        Room b = findById(id);
        if (b != null) {
            repository.delete(b);
        }
    }

    public int saveNew(RoomDto roomDto) {
        Room i = mapper.toEntity(roomDto);
        i.setId(IdGenerator.getInstance().createID());
        return repository.save(i);
    }

    public void update(int id, RoomDto roomDto) {
        Room oldRoom = findById(id);
        if (oldRoom != null) {
            oldRoom.setValveState(mapper.toEntity(roomDto).getValveState());
            oldRoom.setCurrTemp(mapper.toEntity(roomDto).getCurrTemp());
            oldRoom.setInfraredSensor(mapper.toEntity(roomDto).isInfraredSensor());
            oldRoom.setWorkingTemp(mapper.toEntity(roomDto).getWorkingTemp());
            oldRoom.setWaitingTemp(mapper.toEntity(roomDto).getWaitingTemp());
            oldRoom.setBatterySquare(mapper.toEntity(roomDto).getBatterySquare());
            repository.update(oldRoom);
        }
    }
}
