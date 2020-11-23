package ro.agilehub.javacourse.car.hire.user.service;

import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;

import java.util.List;

public interface UserService {

    List<UserDO> findAll();

    UserDO findById(String id);

    UserDO addUser(User user);

}
