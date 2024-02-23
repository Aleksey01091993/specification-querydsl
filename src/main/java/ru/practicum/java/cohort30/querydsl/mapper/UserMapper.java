package ru.practicum.java.cohort30.querydsl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.java.cohort30.querydsl.model.UserFilterModel;
import ru.practicum.java.cohort30.querydsl.model.UserFilterRequest;
import ru.practicum.java.cohort30.querydsl.model.UserModel;
import ru.practicum.java.cohort30.querydsl.model.UserResponse;
import ru.practicum.java.cohort30.querydsl.storage.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserFilterModel toModel(UserFilterRequest request);

    UserModel toModel(UserEntity entity);

    UserResponse toResponse(UserModel model);

    UserEntity toEntity(UserModel model);
}
