package ro.agilehub.javacourse.car.hire.user.service;

import ro.agilehub.javacourse.car.hire.user.domain.UserCountryDO;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;

import java.util.List;

public interface UserService {

    List<UserDO> findAll();

    UserDO findById(String id);

    UserDO addUser(UserDO user);

    UserDO updateUser(UserDO user);

    UserCountryDO findByName(String country);

    void removeUser(String id);
}
