package ru.vsu.cs.mappers;

import ru.vsu.cs.models.Room;
import ru.vsu.cs.models.ValveState;
import ru.vsu.cs.models.dtos.RoomDto;

public class RoomMapper implements Mapper<Room, RoomDto> {

    @Override
    public RoomDto toDTO(Room entity) {
        return new RoomDto(
                entity.getId(),
                entity.getValveState().getValue(),
                entity.getCurrTemp(),
                entity.isInfraredSensor(),
                entity.getWorkingTemp(),
                entity.getWaitingTemp(),
                entity.getBatterySquare(),
                entity.getEnvironment()
        );
    }

    @Override
    public Room toEntity(RoomDto dto) {
        return new Room(
                0,
                ValveState.from(dto.getValveState()),
                dto.getCurrTemp(),
                dto.isInfraredSensor(),
                dto.getWorkingTemp(),
                dto.getWaitingTemp(),
                dto.getBatterySquare(),
                dto.getEnvironment()
        );
    }
}