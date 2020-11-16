package ro.agilehub.javacourse.car.hire.rental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.agilehub.javacourse.car.hire.api.model.RentalDTO;
import ro.agilehub.javacourse.car.hire.api.specification.RentalApi;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@RestController
public class RentalController implements RentalApi {

    private final List<RentalDTO> rentals = new ArrayList<>();

    @Override
    public ResponseEntity<RentalDTO> addRental(@Valid RentalDTO rentalDTO) {
        rentalDTO.setId(1);
        rentalDTO.setStartDateTime(OffsetDateTime.now());
        rentalDTO.setStatus(RentalDTO.StatusEnum.ACTIVE);
        rentals.add(rentalDTO);
        return ResponseEntity.ok(rentalDTO);
    }

    @Override
    public ResponseEntity<RentalDTO> getRental(Integer id) {
        return rentals.stream()
                .filter(rentalDTO -> rentalDTO.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<RentalDTO>> getRentals() {
        return ResponseEntity.ok(rentals);
    }

    @Override
    public ResponseEntity<RentalDTO> updateRental(@Valid RentalDTO rentalDTO) {
        rentals.stream()
                .filter(userDTO1 -> rentalDTO.getId().equals(userDTO1.getId()))
                .findFirst()
                .ifPresent(
                        rentalDTO1 -> {
                            if (!isNull(rentalDTO.getUserId())) {
                                rentalDTO1.setUserId(rentalDTO.getUserId());
                            }
                            if (!isNull(rentalDTO.getCarId())) {
                                rentalDTO1.setCarId(rentalDTO.getCarId());
                            }
                            if (!isNull(rentalDTO.getStartDateTime())) {
                                rentalDTO1.setStartDateTime(rentalDTO.getStartDateTime());
                            }
                            if (!isNull(rentalDTO.getEndDateTime())) {
                                rentalDTO1.setEndDateTime(rentalDTO.getEndDateTime());
                            }
                            if (!isNull(rentalDTO.getStatus())) {
                                rentalDTO1.setStatus(rentalDTO.getStatus());
                            }
                        });
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeRental(Integer id) {
        Optional<RentalDTO> optionalRentalDTO =
                rentals.stream()
                        .filter(rentalDTO -> rentalDTO.getId().equals(id))
                        .findFirst();
        optionalRentalDTO.ifPresent(rentals::remove);
        return ResponseEntity.ok().build();
    }
}
