package ru.vsu.cs.mappers;

import ru.vsu.cs.models.Environment;
import ru.vsu.cs.models.Room;
import ru.vsu.cs.models.dtos.EnvironmentDto;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EnvironmentMapper implements Mapper<Environment, EnvironmentDto> {
    @Override
    public EnvironmentDto toDTO(Environment entity) {
        return new EnvironmentDto(
                entity.getId(),
                entity.getTemp()
//                entity.getEpochTimer() != null ? entity.getEpochTimer().getId() : -1,
//                entity.getBoiler() != null ? entity.getBoiler().getId() : -1,
//                entity.getRooms().size() != 0 ?
//                        entity.getRooms().stream().map(Room::getId).collect(Collectors.toList()) :
//                        new ArrayList<>()
        );
    }

    @Override
    public Environment toEntity(EnvironmentDto dto) {
        return new Environment(
                0,
                dto.getTemp()
//                null,
//                null,
//                new ArrayList<>()
        );
    }
}
