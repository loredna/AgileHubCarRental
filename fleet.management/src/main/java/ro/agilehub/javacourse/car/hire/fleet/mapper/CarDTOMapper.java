package ro.agilehub.javacourse.car.hire.fleet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.agilehub.javacourse.car.hire.api.model.CarDTO;
import ro.agilehub.javacourse.car.hire.fleet.domain.CarDO;

@Mapper(componentModel = "spring", uses = {CarObjectIdMapper.class})
public interface CarDTOMapper {

    @Mapping(target = "id", source = "carDO.id")
    @Mapping(target = "carStatus", source = "carDO.status")
    CarDTO toCarDTO(CarDO carDO);

    @Mapping(target = "status", source = "carDTO.carStatus")
    @Mapping(target = "id", source = "carDTO.id")
    CarDO toCarDO(CarDTO carDTO);
}
