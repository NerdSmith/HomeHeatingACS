package ru.vsu.cs.mappers;

import ru.vsu.cs.models.EpochTimer;
import ru.vsu.cs.models.Room;
import ru.vsu.cs.models.dtos.EpochTimerDto;
import ru.vsu.cs.models.dtos.RoomDto;

public class EpochTimerMapper implements Mapper<EpochTimer, EpochTimerDto> {
    @Override
    public EpochTimerDto toDTO(EpochTimer entity) {
        return new EpochTimerDto(
                entity.getId(),
                entity.getCurrTime(),
                entity.getEnvironment()
        );
    }

    @Override
    public EpochTimer toEntity(EpochTimerDto dto) {
        return new EpochTimer(
                0,
                dto.getCurrTime(),
                dto.getEnvironment()
        );
    }
}
