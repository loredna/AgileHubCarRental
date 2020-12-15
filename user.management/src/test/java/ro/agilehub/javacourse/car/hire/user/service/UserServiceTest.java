package ro.agilehub.javacourse.car.hire.user.service;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ro.agilehub.javacourse.car.hire.user.controller.error.EmailNotFoundException;
import ro.agilehub.javacourse.car.hire.user.controller.error.UserNotFoundException;
import ro.agilehub.javacourse.car.hire.user.controller.error.UsernameNotFoundException;
import ro.agilehub.javacourse.car.hire.user.domain.UserCountryDO;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.entity.UserCountry;
import ro.agilehub.javacourse.car.hire.user.repository.UserCountryRepository;
import ro.agilehub.javacourse.car.hire.user.repository.UserRepository;
import ro.agilehub.javacourse.car.hire.user.service.impl.UserServiceImpl;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserCountryDOMapper;
import ro.agilehub.javacourse.car.hire.user.service.mapper.UserDOMapper;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserCountryRepository userCountryRepository;

    @Mock
    private UserDOMapper mapper;

    @Mock
    private UserCountryDOMapper countryMapper;

    @Test
    public void findById() {
        ObjectId objectId = new ObjectId("507f191e810c19729de860ea");
        UserCountry userCountry = UserCountry.builder().name("RO").build();

        User user = new User();
        user.setId(objectId);
        user.setEmail("test@gmail.com");
        user.setCountry("RO");

        when(userRepository.findById(objectId)).thenReturn(Optional.of(user));
        when(userCountryRepository.findByName("RO")).thenReturn(userCountry);
        when(mapper.toUserDO(user, userCountry)).thenReturn(new UserDO());

        assertNotNull(userService.findById(objectId.toString()));
    }

    @Test
    public void findById_whenUserNotFound_throwsException() {
        assertThrows(UserNotFoundException.class,
                () -> userService.findById(new ObjectId("507f191e810c19729de860ea").toString()));
    }

    @Test
    public void findAllUsers() {
        UserDO userDO = mock(UserDO.class);
        User user = mock(User.class);
        List<User> users = List.of(user);

        when(userRepository.findAll()).thenReturn(users);
        when(mapper.toUserDO(any(), any())).thenReturn(userDO);

        assertEquals(1, userService.findAll().size());
    }

    @Test
    public void addUser() {
        UserDO userDO = UserDO.builder().email("test@gmail.com").username("test").build();
        User user = new User();
        user.setCountry("RO");
        UserCountry userCountry = UserCountry.builder().name("RO").build();

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(mapper.toUser(any(UserDO.class))).thenReturn(user);
        when(mapper.toUserDO(any(), any())).thenReturn(userDO);

        assertNotNull(userService.addUser(userDO));
    }

    @Test
    public void addUser_EmailNotFound_ThrowsException() {
        UserDO userDO = UserDO.builder().username("test").build();
        assertThrows(EmailNotFoundException.class,
                () -> userService.addUser(userDO));
    }

    @Test
    public void addUser_UsernameNotFound_ThrowsException() {
        UserDO userDO = UserDO.builder().email("test").build();
        assertThrows(UsernameNotFoundException.class,
                () -> userService.addUser(userDO));
    }

    @Test
    public void addUser_EmailAlreadyExists_ThrowsException() {
        UserDO userDO = UserDO.builder().email("test").username("test").build();
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(mock(User.class));

        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(userDO));
    }

    @Test
    public void addUser_UsernameAlreadyExists_ThrowsException() {
        UserDO userDO = UserDO.builder().email("test").username("test").build();

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(mock(User.class));

        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(userDO));
    }

    @Test
    public void updateUser() {
        UserDO userDO = UserDO.builder().email("test@gmail.com").username("test").build();
        User user = new User();
        user.setCountry("RO");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(mapper).update(any(), any());
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(mapper.toUserDO(any(), any())).thenReturn(userDO);

        assertNotNull(userService.updateUser(userDO));
    }

    @Test
    public void updateUser_EmailAlreadyExists_ThrowsException() {
        UserDO userDO = UserDO.builder().email("test").username("test").build();
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(mock(User.class));

        assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(userDO));
    }

    @Test
    public void updateUser_UsernameAlreadyExists_ThrowsException() {
        UserDO userDO = UserDO.builder().email("test").username("test").build();

        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        when(userRepository.findByUsername(Mockito.anyString())).thenReturn(mock(User.class));

        assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(userDO));
    }

    @Test
    public void updateUser_ThrowsException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> userService.updateUser(new UserDO()));
    }

    @Test
    public void findByName() {
        UserCountry userCountry = UserCountry.builder().name("RO").build();
        when(userCountryRepository.findByName(Mockito.any())).thenReturn(userCountry);
        when(countryMapper.toDomainObject(Mockito.any())).thenReturn(new UserCountryDO());

        assertNotNull(userService.findByName("RO"));
    }

    @Test
    public void removeUser() {
        ObjectId objectId = new ObjectId("507f191e810c19729de860ea");
        userService.removeUser(objectId.toString());

        Mockito.verify(userRepository).delete(any(User.class));
    }
}
