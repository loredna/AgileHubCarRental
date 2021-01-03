package ro.agilehub.javacourse.car.hire.user.mapper;

import org.mapstruct.*;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface UserDTOMapper {

    @Mapping(target = "id", source = "userDO.id")
    @Mapping(target = "country.id", expression = "java(userCountryDO.getId().toString())")
    @Mapping(target = "country.name", source = "userDO.country.name")
    @Mapping(target = "country.isoCode", source = "userDO.country.isoCode")
    UserDTO toUserDTO(UserDO userDO);

    @Mapping(target = "id", source = "userDTO.id")
    UserDO toUserDO(UserDTO userDTO);
}

