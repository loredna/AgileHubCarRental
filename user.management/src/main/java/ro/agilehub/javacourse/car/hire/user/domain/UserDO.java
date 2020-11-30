package ro.agilehub.javacourse.car.hire.user.domain;

import lombok.*;
import org.bson.types.ObjectId;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDO {

    private ObjectId id;

    private String email;

    private String password;

    private String username;

    private String firstName;

    private String lastName;

    private UserCountryDO country;

    private String licenseNumber;

    private UserDTO.StatusEnum status;

    private String title;

}
