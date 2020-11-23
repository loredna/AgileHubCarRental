package ro.agilehub.javacourse.car.hire.user.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.entity.UserCountry;

@Mapper(componentModel = "spring", uses = {UserCountryDOMapper.class, ObjectIdMapper.class})
public interface UserDOMapper {

    @Mapping(target = "country", source = "userCountry")
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "licenseNumber", source = "user.licenseNumber")
    @Mapping(target = "status", source = "user.status")
    UserDO toDomainObject(User user, UserCountry userCountry);

}

