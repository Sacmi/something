package ru.sacmi.something.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.sacmi.something.dto.UserGetDto;
import ru.sacmi.something.dto.UserUpdateDto;
import ru.sacmi.something.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    UserGetDto userEntityToUserGetDto(UserEntity userEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserEntity updateUserEntityFromDto(UserUpdateDto userUpdateDto, @MappingTarget UserEntity userEntity);
}
