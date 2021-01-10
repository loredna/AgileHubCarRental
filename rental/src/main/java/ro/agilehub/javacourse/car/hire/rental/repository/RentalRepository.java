package ro.agilehub.javacourse.car.hire.rental.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.agilehub.javacourse.car.hire.rental.entity.Rental;

@Repository
public interface RentalRepository extends MongoRepository<Rental, ObjectId> {
}
