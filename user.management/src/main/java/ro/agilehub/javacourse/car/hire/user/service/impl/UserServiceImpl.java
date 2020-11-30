package ro.agilehub.javacourse.car.hire.user.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.agilehub.javacourse.car.hire.user.controller.error.EmailNotFoundException;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.repository.UserCountryDAO;
import ro.agilehub.javacourse.car.hire.user.repository.UserDAO;
import ro.agilehub.javacourse.car.hire.user.service.UserService;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserCountryDOMapper;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserDOMapper;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

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
    public UserDO addUser(User user) {
        if (user.getEmail() == null) {
            throw new EmailNotFoundException("No email found!");
        }
        return map(userDAO.save(user));
    }

    @Override
    public UserDO updateUser(User user) {
        Optional<User> optionalUser = userDAO.findById(user.getId());

        optionalUser
                .ifPresent(updatedUser -> {
                            if (isNotBlank(user.getFirstName())) {
                                updatedUser.setFirstName(user.getFirstName());
                            }
                            if (isNotBlank(user.getLastName())) {
                                updatedUser.setLastName(user.getLastName());
                            }
                            if (isNotBlank(user.getCountry())) {
                                updatedUser.setCountry(user.getCountry());
                            }
                            if (isNotBlank(user.getLicenseNumber())) {
                                updatedUser.setLicenseNumber(user.getLicenseNumber());
                            }
                            if (!isNull(user.getStatus())) {
                                updatedUser.setStatus(user.getStatus());
                            }
                            if (!isNull(user.getTitle())) {
                                updatedUser.setTitle(user.getTitle());
                            }
                        }
                );
        return optionalUser
                .map(updatedUser -> map(userDAO.save(updatedUser)))
                .orElseThrow();
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
        return mapper.toDomainObject(user, userCountry);
    }
}
