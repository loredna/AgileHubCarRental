package ro.agilehub.javacourse.car.hire.user.service.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.agilehub.javacourse.car.hire.user.controller.error.EmailNotFoundException;
import ro.agilehub.javacourse.car.hire.user.controller.error.UserNotFoundException;
import ro.agilehub.javacourse.car.hire.user.controller.error.UsernameNotFoundException;
import ro.agilehub.javacourse.car.hire.user.domain.UserCountryDO;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.repository.UserCountryRepository;
import ro.agilehub.javacourse.car.hire.user.repository.UserRepository;
import ro.agilehub.javacourse.car.hire.user.service.UserService;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserCountryDOMapper;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserDOMapper;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCountryRepository userCountryRepository;

    @Autowired
    private UserDOMapper mapper;

    @Autowired
    private UserCountryDOMapper countryMapper;

    @Override
    public UserDO findById(String id) {
        return userRepository.findById(new ObjectId(id))
                .map(this::map)
                .orElseThrow(() -> {
                    throw new UserNotFoundException("User not found.");
                });
    }

    @Override
    public List<UserDO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::map)
                .collect(toList());
    }

    @Override
    public UserDO addUser(UserDO userDO) {
        String email = userDO.getEmail();
        String username = userDO.getUsername();
        if (email == null) {
            throw new EmailNotFoundException("No email found.");
        }
        if (username == null) {
            throw new UsernameNotFoundException("No email found.");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        return map(userRepository.save(mapper.toUser(userDO)));
    }

    @Override
    public UserDO updateUser(UserDO userDO) {
        if (userRepository.findByEmail(userDO.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }
        if (userRepository.findByUsername(userDO.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }

        Optional<User> optionalUser = userRepository.findById(userDO.getId());
        optionalUser
                .ifPresent(user -> mapper.update(userDO, user));
        return optionalUser
                .map(updatedUser -> map(userRepository.save(updatedUser)))
                .orElseThrow(() -> {
                    throw new IllegalStateException("Failed to update user.");
                });
    }

    @Override
    public UserCountryDO findByName(String country) {
        return countryMapper.toDomainObject(userCountryRepository.findByName(country));
    }

    @Override
    public void removeUser(String id) {
        User user = User.builder().id(new ObjectId(id)).build();
        userRepository.delete(user);
    }

    private UserDO map(User user) {
        var userCountry = userCountryRepository
                .findByName(user.getCountry());
        return mapper.toUserDO(user, userCountry);
    }
}
