package ro.agilehub.javacourse.car.hire.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@EqualsAndHashCode(of = "_id")
@Document(collection = "country")
public class UserCountry {

    @Id
    @Field("_id")
    private ObjectId id;

    private String name;

    private String isoCode;

}
