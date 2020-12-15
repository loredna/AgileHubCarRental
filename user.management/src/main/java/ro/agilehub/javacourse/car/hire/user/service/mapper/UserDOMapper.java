package ro.agilehub.javacourse.car.hire.user.service.mapper;

import org.mapstruct.*;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.entity.UserCountry;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface UserDOMapper {

    @Mapping(target = "country", source = "userCountry")
    @Mapping(target = "id", source = "user.id")
    UserDO toUserDO(User user, UserCountry userCountry);

    @Mapping(target = "id", source = "userDO.id")
    @Mapping(target = "country", source = "userDO.country.name")
    User toUser(UserDO userDO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "country", source = "userDO.country.name")
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    void update(UserDO userDO, @MappingTarget User user);
}

