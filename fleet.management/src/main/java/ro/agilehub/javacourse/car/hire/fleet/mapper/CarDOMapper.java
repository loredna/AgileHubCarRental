package ro.agilehub.javacourse.car.hire.fleet.mapper;

import org.mapstruct.*;
import ro.agilehub.javacourse.car.hire.fleet.domain.CarDO;
import ro.agilehub.javacourse.car.hire.fleet.entity.Car;

@Mapper(componentModel = "spring", uses = {CarObjectIdMapper.class})
public interface CarDOMapper {

    @Mapping(target = "id", source = "car.id")
    CarDO toCarDO(Car car);

    @Mapping(target = "id", source = "carDO.id")
    Car toCar(CarDO carDO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void update(CarDO carDO, @MappingTarget Car car);
}

