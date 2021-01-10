package ro.agilehub.javacourse.car.hire.fleet.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ro.agilehub.javacourse.car.hire.api.model.CarDTO;

@Data
@EqualsAndHashCode(of = "_id")
@Document(collection = "fleet")
public class Car {

    @Id
    @Field("_id")
    private ObjectId id;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private String fuel;
    private String sizeClass;
    private CarDTO.CarStatusEnum status;
}
