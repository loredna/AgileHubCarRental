package ro.agilehub.javacourse.car.hire.user.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.service.mapper.ObjectIdMapper;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface UserMapper {

    @Mapping(target = "status", defaultValue = "ACTIVE")
    User toDomainObject(UserDTO userDTO);
}

