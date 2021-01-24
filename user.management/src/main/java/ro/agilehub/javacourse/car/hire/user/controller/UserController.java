package ro.agilehub.javacourse.car.hire.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;
import ro.agilehub.javacourse.car.hire.api.specification.UserApi;
import ro.agilehub.javacourse.car.hire.user.domain.UserDO;
import ro.agilehub.javacourse.car.hire.user.mapper.UserDTOMapper;
import ro.agilehub.javacourse.car.hire.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@PreAuthorize("hasAuthority('MANAGER')")
@RequiredArgsConstructor
public class UserController implements UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOMapper mapper;

    @Override
    public ResponseEntity<UserDTO> addUser(@Valid UserDTO userDTO) {
        UserDO userDO = mapper.toUserDO(userDTO);
        return ResponseEntity.ok(mapper.toUserDTO(userService.addUser(userDO)));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDO> users = userService.findAll();
        return ResponseEntity.ok(users.stream()
                .map(userDO -> mapper.toUserDTO(userDO))
                .collect(toList()));
    }

    @Override
    @PreAuthorize("hasAnyAuthority('MANAGER','CUSTOMER')")
    public ResponseEntity<UserDTO> getUser(String id) {
        UserDO userDO = userService.findById(id);
        return ResponseEntity.ok(mapper.toUserDTO(userDO));
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(@Valid UserDTO userDTO) {
        UserDO userDO = mapper.toUserDO(userDTO);
        return ResponseEntity.ok(mapper.toUserDTO(userService.updateUser(userDO)));
    }

    @Override
    public ResponseEntity<Void> removeUser(String id) {
        userService.removeUser(id);
        return ResponseEntity.ok().build();
    }
}
