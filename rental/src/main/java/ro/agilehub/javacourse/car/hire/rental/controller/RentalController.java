package ro.agilehub.javacourse.car.hire.rental.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ro.agilehub.javacourse.car.hire.api.model.RentalDTO;
import ro.agilehub.javacourse.car.hire.api.specification.RentalApi;
import ro.agilehub.javacourse.car.hire.rental.domain.RentalDO;
import ro.agilehub.javacourse.car.hire.rental.mapper.RentalDTOMapper;
import ro.agilehub.javacourse.car.hire.rental.service.RentalService;

import javax.validation.Valid;

@RestController
@PreAuthorize("hasAuthority('MANAGER')")
@RequiredArgsConstructor
public class RentalController implements RentalApi {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalDTOMapper mapper;

    @Override
    public ResponseEntity<RentalDTO> addRental(@Valid RentalDTO rentalDTO) {
        RentalDO rentalDO = mapper.toRentalDO(rentalDTO);
        rentalDO.setStatus(RentalDTO.RentalStatusEnum.ACTIVE);
        return ResponseEntity.ok(mapper.toRentalDTO(rentalService.createNewRental(rentalDO)));
    }
}
