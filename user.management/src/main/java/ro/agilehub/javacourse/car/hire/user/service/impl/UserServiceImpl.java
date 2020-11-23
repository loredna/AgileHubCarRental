package ro.agilehub.javacourse.car.hire.user.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.repository.UserCountryDAO;
import ro.agilehub.javacourse.car.hire.user.repository.UserDAO;
import ro.agilehub.javacourse.car.hire.user.service.UserService;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserDOMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserCountryDAO userCountryDAO;

    @Autowired
    private UserDOMapper mapper;

    @Override
    public List<UserDO> findAll() {
        return userDAO.findAll()
                .stream()
                .map(this::map)
                .collect(toList());
    }

    @Override
    public UserDO findById(String id) {
        return userDAO.findById(new ObjectId(id))
                .map(this::map)
                .orElseThrow();
    }

    @Override
    public UserDO addUser(User user) {
        return map(userDAO.save(user));
    }

    private UserDO map(User user) {
        var userCountry = userCountryDAO
                .findById(new ObjectId(user.getCountry()))
                .orElse(null);
        return mapper.toDomainObject(user, userCountry);
    }
}
