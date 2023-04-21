package ru.vsu.cs.mappers;

public interface Mapper<T, D> {
    D toDTO(T entity);
    T toEntity(D dto);
}
