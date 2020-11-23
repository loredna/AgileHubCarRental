package ro.agilehub.javacourse.car.hire.user.domain;

import lombok.*;
import org.bson.types.ObjectId;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCountryDO {

    private ObjectId id;

    private String name;

    private String isoCode;

}
