package ro.agilehub.javacourse.car.hire.fleet.service;

import ro.agilehub.javacourse.car.hire.fleet.domain.CarDO;

import java.util.List;

public interface FleetService {

    List<CarDO> findAll();

    CarDO findById(String id);

    CarDO addCar(CarDO user);

    CarDO updateCar(CarDO user);

    void removeCar(String id);
}
