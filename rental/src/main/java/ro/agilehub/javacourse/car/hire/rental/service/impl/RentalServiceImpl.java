package ro.agilehub.javacourse.car.hire.rental.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.agilehub.javacourse.car.hire.rental.domain.RentalDO;
import ro.agilehub.javacourse.car.hire.rental.mapper.RentalDOMapper;
import ro.agilehub.javacourse.car.hire.rental.repository.RentalRepository;
import ro.agilehub.javacourse.car.hire.rental.service.RentalService;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalDOMapper mapper;

    @Override
    public RentalDO createNewRental(RentalDO rentalDO) {
        //todo: add some validation
        return mapper.toRentalDO(rentalRepository.save(mapper.toRental(rentalDO)));
    }
}
