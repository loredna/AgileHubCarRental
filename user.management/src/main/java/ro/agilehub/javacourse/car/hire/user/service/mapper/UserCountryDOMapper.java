package ro.agilehub.javacourse.car.hire.user.service.mapper;

import org.mapstruct.Mapper;
import ro.agilehub.javacourse.car.hire.user.domain.UserCountryDO;
import ro.agilehub.javacourse.car.hire.user.entity.UserCountry;

@Mapper(componentModel = "spring", uses = ObjectIdMapper.class)
public interface UserCountryDOMapper {

    UserCountryDO toDomainObject(UserCountry userCountry);

}
