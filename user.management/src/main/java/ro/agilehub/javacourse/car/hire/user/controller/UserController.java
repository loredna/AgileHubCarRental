package ro.agilehub.javacourse.car.hire.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ro.agilehub.javacourse.car.hire.api.model.UserDTO;
import ro.agilehub.javacourse.car.hire.api.specification.UserApi;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RestController
public class UserController implements UserApi {

    private final List<UserDTO> users = new ArrayList<>();

    @Override
    public ResponseEntity<UserDTO> addUser(@Valid UserDTO userDTO) {
        userDTO.setId(1);
        userDTO.setStatus(UserDTO.StatusEnum.ACTIVE);
        users.add(userDTO);
        return ResponseEntity.ok(userDTO);
    }

    @Override
    public ResponseEntity<UserDTO> getUser(Integer id) {
        return users.stream()
                .filter(userDTO -> userDTO.getId().equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(@Valid UserDTO userDTO) {
        users.stream()
                .filter(userDTO1 -> userDTO.getId().equals(userDTO1.getId()))
                .findFirst()
                .ifPresent(
                        userDTO1 -> {
                            if (isNotBlank(userDTO.getFirstName())) {
                                userDTO1.setFirstName(userDTO.getFirstName());
                            }
                            if (isNotBlank(userDTO.getLastName())) {
                                userDTO1.setLastName(userDTO.getLastName());
                            }
                            if (isNotBlank(userDTO.getCountry())) {
                                userDTO1.setCountry(userDTO.getCountry());
                            }
                            if (isNotBlank(userDTO.getLicenseNumber())) {
                                userDTO1.setLicenseNumber(userDTO.getLicenseNumber());
                            }
                            if (!isNull(userDTO.getStatus())) {
                                userDTO1.setStatus(userDTO.getStatus());
                            }
                        });
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeUser(Integer id) {
        Optional<UserDTO> optionalUserDTO =
                users.stream()
                        .filter(userDTO -> userDTO.getId().equals(id))
                        .findFirst();
        optionalUserDTO.ifPresent(users::remove);
        return ResponseEntity.ok().build();
    }
}
