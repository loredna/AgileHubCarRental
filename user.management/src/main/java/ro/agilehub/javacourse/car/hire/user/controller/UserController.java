package ro.agilehub.javacourse.car.hire.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;
import ro.agilehub.javacourse.car.hire.api.specification.UserApi;
import ro.agilehub.javacourse.car.hire.user.controller.mapper.UserDTOMapper;
import ro.agilehub.javacourse.car.hire.user.controller.mapper.UserMapper;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOMapper userDTOMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseEntity<UserDTO> addUser(@Valid UserDTO userDTO) {
        UserDO userDO = userService.addUser(userMapper.toDomainObject(userDTO));
        return ResponseEntity.ok(userDTOMapper.toDomainObject(userDO));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDO> users = userService.findAll();
        return ResponseEntity.ok(users.stream()
                .map(userDO -> userDTOMapper.toDomainObject(userDO))
                .collect(toList()));
    }

    @Override
    public ResponseEntity<UserDTO> getUser(String id) {
        UserDO userDO = userService.findById(id);
        return ResponseEntity.ok(userDTOMapper.toDomainObject(userDO));
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(@Valid UserDTO userDTO) {
        UserDO userDO = userService.updateUser(userMapper.toDomainObject(userDTO));
        return ResponseEntity.ok(userDTOMapper.toDomainObject(userDO));
    }

    @Override
    public ResponseEntity<Void> removeUser(String id) {
        userService.removeUser(id);
        return ResponseEntity.ok().build();
    }
}
