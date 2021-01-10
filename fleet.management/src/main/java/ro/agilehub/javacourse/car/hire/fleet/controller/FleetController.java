package ro.agilehub.javacourse.car.hire.fleet.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ro.agilehub.javacourse.car.hire.api.model.CarDTO;
import ro.agilehub.javacourse.car.hire.api.specification.FleetApi;
import ro.agilehub.javacourse.car.hire.fleet.domain.CarDO;
import ro.agilehub.javacourse.car.hire.fleet.mapper.CarDTOMapper;
import ro.agilehub.javacourse.car.hire.fleet.service.FleetService;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@PreAuthorize("hasAuthority('MANAGER')")
@RequiredArgsConstructor
public class FleetController implements FleetApi {

    @Autowired
    private FleetService fleetService;

    @Autowired
    private CarDTOMapper mapper;

    @Override
    public ResponseEntity<CarDTO> addCar(@Valid CarDTO carDTO) {
        CarDO carDO = mapper.toCarDO(carDTO);
        carDO.setStatus(CarDTO.CarStatusEnum.ACTIVE);
        return ResponseEntity.ok(mapper.toCarDTO(fleetService.addCar(carDO)));
    }

    @Override
    public ResponseEntity<CarDTO> getCar(String id) {
        CarDO carDO = fleetService.findById(id);
        return ResponseEntity.ok(mapper.toCarDTO(carDO));
    }

    @Override
    public ResponseEntity<List<CarDTO>> getCars() {
        List<CarDO> cars = fleetService.findAll();
        return ResponseEntity.ok(cars.stream()
                .map(carDO -> mapper.toCarDTO(carDO))
                .collect(toList()));
    }

    @Override
    public ResponseEntity<CarDTO> updateCar(@Valid CarDTO carDTO) {
        CarDO carDO = mapper.toCarDO(carDTO);
        return ResponseEntity.ok(mapper.toCarDTO(fleetService.updateCar(carDO)));
    }

    @Override
    public ResponseEntity<Void> removeCar(String id) {
        fleetService.removeCar(id);
        return ResponseEntity.ok().build();
    }
}
