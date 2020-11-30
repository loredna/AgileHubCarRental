package ro.agilehub.javacourse.car.hire.user.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.service.mapper.ObjectIdMapper;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface UserDTOMapper {

    @Mapping(target = "id", source = "userDO.id")
    @Mapping(target = "password", constant = "***")
    @Mapping(target = "country", source = "userDO.country.name")
    UserDTO toDomainObject(UserDO userDO);
}

