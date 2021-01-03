package ro.agilehub.javacourse.car.hire.user.util;

import org.bson.types.ObjectId;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.UserCountry;

public class TestUtil {

    public static UserDO getUserDO() {
        return UserDO.builder().email("test@gmail.com").username("test").build();
    }

    public static UserCountry getUserCountryRO() {
        return UserCountry.builder().name("RO").build();
    }

    public static String getObjectId(){
        return new ObjectId("507f191e810c19729de860ea").toString();
    }
}
