package ro.agilehub.javacourse.car.hire.fleet.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import ro.agilehub.javacourse.car.hire.api.model.CarDTO;
import ro.agilehub.javacourse.car.hire.fleet.domain.CarDO;
import ro.agilehub.javacourse.car.hire.fleet.entity.Car;
import ro.agilehub.javacourse.car.hire.fleet.mapper.CarDOMapper;
import ro.agilehub.javacourse.car.hire.fleet.repository.CarRepository;
import ro.agilehub.javacourse.car.hire.fleet.service.FleetService;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class FleetServiceImpl implements FleetService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarDOMapper mapper;

    @Override
    public List<CarDO> findAll() {
        return carRepository.findAll()
                .stream()
                .map(car -> mapper.toCarDO(car))
                .collect(toList());
    }

    @Override
    public CarDO findById(String id) {
        return carRepository.findById(new ObjectId(id))
                .map(car -> mapper.toCarDO(car))
                .orElseThrow(() -> {
                    throw new NotFoundException("User not found.");
                });
    }

    @Override
    public CarDO addCar(CarDO carDO) {
        //todo: validation to be added
        return mapper.toCarDO(carRepository.save(mapper.toCar(carDO)));
    }

    @Override
    public CarDO updateCar(CarDO carDO) {
        Optional<Car> optionalUser = carRepository.findById(carDO.getId());
        optionalUser
                .ifPresent(car -> mapper.update(carDO, car));
        return optionalUser
                .map(updatedCar -> mapper.toCarDO(carRepository.save(updatedCar)))
                .orElseThrow(() -> {
                    throw new IllegalStateException("Failed to update car.");
                });
    }

    @Override
    public void removeCar(String id) {
        Car car = carRepository.findById(new ObjectId(id)).orElseThrow();
        car.setStatus(CarDTO.CarStatusEnum.DELETED);
        carRepository.save(car);
    }
}
