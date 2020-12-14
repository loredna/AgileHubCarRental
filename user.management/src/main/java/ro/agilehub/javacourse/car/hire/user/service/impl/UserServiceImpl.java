package ro.agilehub.javacourse.car.hire.user.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.agilehub.javacourse.car.hire.user.controller.error.EmailNotFoundException;
import ro.agilehub.javacourse.car.hire.user.domain.UserCountryDO;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.repository.UserCountryDAO;
import ro.agilehub.javacourse.car.hire.user.repository.UserDAO;
import ro.agilehub.javacourse.car.hire.user.service.UserService;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserCountryDOMapper;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserDOMapper;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserCountryDAO userCountryDAO;

    @Autowired
    private UserDOMapper mapper;

    @Autowired
    private UserCountryDOMapper countryMapper;

    @Override
    public UserDO findById(String id) {
        return userDAO.findById(new ObjectId(id))
                .map(this::map)
                .orElseThrow();
    }

    @Override
    public List<UserDO> findAll() {
        return userDAO.findAll()
                .stream()
                .map(this::map)
                .collect(toList());
    }

    @Override
    public UserDO addUser(UserDO userDO) {
        if (userDO.getEmail() == null) {
            throw new EmailNotFoundException("No email found!");
        }
        return map(userDAO.save(mapper.toUser(userDO)));
    }

    @Override
    public UserDO updateUser(UserDO userDO) {
        Optional<User> optionalUser = userDAO.findById(userDO.getId());
        optionalUser
                .ifPresent(user -> mapper.update(userDO, user));
        return optionalUser
                .map(updatedUser -> map(userDAO.save(updatedUser)))
                .orElseThrow();
    }

    @Override
    public UserCountryDO findByName(String country) {
        return countryMapper.toDomainObject(userCountryDAO.findByName(country));
    }

    @Override
    public void removeUser(String id) {
        User user = new User();
        user.setId(new ObjectId(id));
        userDAO.delete(user);
    }

    private UserDO map(User user) {
        var userCountry = userCountryDAO
                .findByName(user.getCountry());
        return mapper.toUserDO(user, userCountry);
    }
}
