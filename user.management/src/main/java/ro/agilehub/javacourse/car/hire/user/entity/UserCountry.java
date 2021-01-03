package ro.agilehub.javacourse.car.hire.user.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "_id")
@Document(collection = "country")
public class UserCountry {

    @Id
    @Field("_id")
    private ObjectId id;

    private String name;

    private String isoCode;

}
