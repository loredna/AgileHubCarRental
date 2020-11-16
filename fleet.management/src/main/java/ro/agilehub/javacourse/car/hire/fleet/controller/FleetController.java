package ro.agilehub.javacourse.car.hire.fleet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.agilehub.javacourse.car.hire.api.model.CarDTO;
import ro.agilehub.javacourse.car.hire.api.specification.FleetApi;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
public class FleetController implements FleetApi {

    private final List<CarDTO> cars = new ArrayList<>();

    @Override
    public ResponseEntity<CarDTO> addCar(@Valid CarDTO carDTO) {
        carDTO.setId(1);
        carDTO.setStatus(CarDTO.StatusEnum.ACTIVE);
        cars.add(carDTO);
        return ResponseEntity.ok(carDTO);
    }

    @Override
    public ResponseEntity<CarDTO> getCar(Integer id) {
        return cars.stream()
                .filter(carDTO -> carDTO.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<CarDTO>> getCars() {
        return ResponseEntity.ok(cars);
    }

    @Override
    public ResponseEntity<CarDTO> updateCar(@Valid CarDTO carDTO) {
        cars.stream()
                .filter(carDTO1 -> carDTO.getId().equals(carDTO1.getId()))
                .findFirst()
                .ifPresent(
                        carDTO1 -> {
                            if (isNotBlank(carDTO.getMake())) {
                                carDTO1.setMake(carDTO.getMake());
                            }
                            if (isNotBlank(carDTO.getModel())) {
                                carDTO1.setModel(carDTO.getModel());
                            }
                            if (!isNull(carDTO.getMileage())) {
                                carDTO1.setMileage(carDTO.getMileage());
                            }
                            if (isNotBlank(carDTO.getFuel())) {
                                carDTO1.setFuel(carDTO.getFuel());
                            }
                            if (isNotBlank(carDTO.getSizeClass())) {
                                carDTO1.setSizeClass(carDTO.getSizeClass());
                            }
                            if (!isNull(carDTO.getStatus())) {
                                carDTO1.setStatus(carDTO.getStatus());
                            }
                        });
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeCar(Integer id) {
        Optional<CarDTO> optionalCarDTO =
                cars.stream()
                        .filter(carDTO -> carDTO.getId().equals(id))
                        .findFirst();
        optionalCarDTO.ifPresent(cars::remove);
        return ResponseEntity.ok().build();
    }
}
