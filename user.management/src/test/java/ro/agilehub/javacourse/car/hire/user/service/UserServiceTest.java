package ro.agilehub.javacourse.car.hire.user.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ro.agilehub.javacourse.car.hire.user.exception.EmailNotFoundException;
import ro.agilehub.javacourse.car.hire.user.exception.UserNotFoundException;
import ro.agilehub.javacourse.car.hire.user.exception.UsernameNotFoundException;
import ro.agilehub.javacourse.car.hire.user.domain.UserCountryDO;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.entity.User;
import ro.agilehub.javacourse.car.hire.user.entity.UserCountry;
import ro.agilehub.javacourse.car.hire.user.repository.UserCountryRepository;
import ro.agilehub.javacourse.car.hire.user.repository.UserRepository;
import ro.agilehub.javacourse.car.hire.user.service.impl.UserServiceImpl;
import ro.agilehub.javacourse.car.hire.user.mapper.UserCountryDOMapper;
import ro.agilehub.javacourse.car.hire.user.mapper.UserDOMapper;
import ro.agilehub.javacourse.car.hire.user.util.TestUtil;

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
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(userCountryRepository.findByName(any())).thenReturn(new UserCountry());
        when(mapper.toUserDO(any(), any())).thenReturn(new UserDO());

        assertNotNull(userService.findById(TestUtil.getObjectId()));
    }

    @Test
    public void findById_whenUserNotFound_throwsException() {
        assertThrows(UserNotFoundException.class,
                () -> userService.findById(TestUtil.getObjectId()));
    }

    @Test
    public void findAllUsers() {
        List<User> users = List.of(mock(User.class));

        when(userRepository.findAll()).thenReturn(users);
        when(mapper.toUserDO(any(), any())).thenReturn(new UserDO());

        assertEquals(1, userService.findAll().size());
    }

    @Test
    public void addUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(new User());
        when(mapper.toUser(any())).thenReturn(new User());
        when(mapper.toUserDO(any(), any())).thenReturn(new UserDO());

        assertNotNull(userService.addUser(TestUtil.getUserDO()));
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
        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(TestUtil.getUserDO()));
    }

    @Test
    public void addUser_UsernameAlreadyExists_ThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.findByUsername(anyString())).thenReturn(new User());

        assertThrows(IllegalArgumentException.class,
                () -> userService.addUser(TestUtil.getUserDO()));
    }

    @Test
    public void updateUser() {
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        Mockito.doNothing().when(mapper).update(any(), any());
        when(userRepository.save(any())).thenReturn(new User());
        when(mapper.toUserDO(any(), any())).thenReturn(new UserDO());

        assertNotNull(userService.updateUser(TestUtil.getUserDO()));
    }

    @Test
    public void updateUser_EmailAlreadyExists_ThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(new User());

        assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(TestUtil.getUserDO()));
    }

    @Test
    public void updateUser_UsernameAlreadyExists_ThrowsException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(userRepository.findByUsername(anyString())).thenReturn(new User());

        assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(TestUtil.getUserDO()));
    }

    @Test
    public void updateUser_ThrowsException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,
                () -> userService.updateUser(new UserDO()));
    }

    @Test
    public void findByName() {
        when(userCountryRepository.findByName(any())).thenReturn(TestUtil.getUserCountryRO());
        when(countryMapper.toDomainObject(any())).thenReturn(new UserCountryDO());

        assertNotNull(userService.findByName("RO"));
    }

    @Test
    public void removeUser() {
        userService.removeUser(TestUtil.getObjectId());
        Mockito.verify(userRepository).delete(any());
    }
}
