package ru.vsu.cs.mappers;

import ru.vsu.cs.models.Boiler;
import ru.vsu.cs.models.BoilerState;
import ru.vsu.cs.models.dtos.BoilerDto;

public class BoilerMapper implements Mapper<Boiler, BoilerDto> {
    @Override
    public BoilerDto toDTO(Boiler entity) {
        return new BoilerDto(
                entity.getId(),
                entity.getBoilerState().getValue(),
                entity.getEnvironment()
        );
    }

    @Override
    public Boiler toEntity(BoilerDto dto) {
        return new Boiler(
                0,
                BoilerState.from(dto.getBoilerState()),
                dto.getEnvironment()
        );
    }
}
