package ro.agilehub.javacourse.car.hire.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;

@Data
@EqualsAndHashCode(of = "_id")
@Document(collection = "user")
public class User {

    @Id
    @Field("_id")
    private ObjectId id;

    private String username;

    private String firstName;

    private String lastName;

    private String country;

    private String licenseNumber;

    private UserDTO.StatusEnum status;

}
