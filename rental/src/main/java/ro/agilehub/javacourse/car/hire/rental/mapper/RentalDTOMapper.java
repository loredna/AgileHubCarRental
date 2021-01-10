package ro.agilehub.javacourse.car.hire.rental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.agilehub.javacourse.car.hire.api.model.RentalDTO;
import ro.agilehub.javacourse.car.hire.rental.domain.RentalDO;

@Mapper(componentModel = "spring", uses = {RentalObjectIdMapper.class})
public interface RentalDTOMapper {

    @Mapping(target = "id", source = "rentalDO.id")
    @Mapping(target = "rentalStatus", source = "rentalDO.status")
    RentalDTO toRentalDTO(RentalDO rentalDO);

    @Mapping(target = "id", source = "rentalDTO.id")
    @Mapping(target = "status", source = "rentalDTO.rentalStatus")
    RentalDO toRentalDO(RentalDTO rentalDTO);
}
