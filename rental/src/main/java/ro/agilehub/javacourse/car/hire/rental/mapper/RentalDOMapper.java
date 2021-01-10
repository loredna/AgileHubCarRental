package ro.agilehub.javacourse.car.hire.rental.mapper;

import org.mapstruct.*;
import ro.agilehub.javacourse.car.hire.rental.domain.RentalDO;
import ro.agilehub.javacourse.car.hire.rental.entity.Rental;

@Mapper(componentModel = "spring", uses = {RentalObjectIdMapper.class})
public interface RentalDOMapper {

    @Mapping(target = "id", source = "rental.id")
    @Mapping(target = "startDateTime", expression = "java(rental.getStartDateTime() == null? null : java.time.OffsetDateTime.ofInstant(rental.getStartDateTime().toInstant(), java.time.ZoneId.systemDefault()))")
    @Mapping(target = "endDateTime", expression = "java(rental.getEndDateTime() == null? null : java.time.OffsetDateTime.ofInstant(rental.getEndDateTime().toInstant(), java.time.ZoneId.systemDefault()))")
    RentalDO toRentalDO(Rental rental);

    @Mapping(target = "id", source = "rentalDO.id")
    //todo: review it - not working yet, error message: org.bson.codecs.configuration.CodecConfigurationException: Can't find a codec for class java.time.OffsetDateTime.
    @Mapping(target = "startDateTime", expression = "java(rentalDO.getStartDateTime() == null? null : java.time.OffsetDateTime.ofInstant(rentalDO.getStartDateTime().toInstant(), java.time.ZoneId.systemDefault()))")
    @Mapping(target = "endDateTime", expression = "java(rentalDO.getEndDateTime() == null? null : java.time.OffsetDateTime.ofInstant(rentalDO.getEndDateTime().toInstant(), java.time.ZoneId.systemDefault()))")
    Rental toRental(RentalDO rentalDO);
}

