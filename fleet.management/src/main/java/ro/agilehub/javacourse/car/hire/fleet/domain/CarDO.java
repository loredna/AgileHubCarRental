package ro.agilehub.javacourse.car.hire.fleet.domain;

import lombok.*;
import org.bson.types.ObjectId;
import ro.agilehub.javacourse.car.hire.api.model.CarDTO;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDO {

    private ObjectId id;

    //todo: create enums for make, model and store them into DB
    private String make;
    private String model;

    private int year;
    private int mileage;
    private String fuel;
    private String sizeClass;
    private CarDTO.CarStatusEnum status;
}
