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
                entity.getTemp(),
                entity.getEpochTimer().getId(),
                entity.getBoiler().getId(),
                entity.getRooms().stream().map(Room::getId).collect(Collectors.toList())
        );
    }

    @Override
    public Environment toEntity(EnvironmentDto dto) {
        return new Environment(
                0,
                dto.getTemp(),
                null,
                null,
                new ArrayList<>()
        );
    }
}
