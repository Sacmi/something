package ru.sacmi.something.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sacmi.something.dto.UserCreateDto;
import ru.sacmi.something.dto.UserGetDto;
import ru.sacmi.something.dto.UserUpdateDto;
import ru.sacmi.something.entity.UserEntity;
import ru.sacmi.something.exception.NotFoundException;
import ru.sacmi.something.mapper.MapStructMapper;
import ru.sacmi.something.service.UserService;

import java.security.Principal;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@RestController
@RequestMapping("/user/")
public class UserController {
    UserService userService;
    MapStructMapper mapStructMapper;

    @PostMapping
    public ResponseEntity<UserGetDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        UserEntity userEntity = userService.createUser(userCreateDto);

        return ResponseEntity.ok(mapStructMapper.userEntityToUserGetDto(userEntity));
    }

    @GetMapping
    public ResponseEntity<UserGetDto> getCurrentUser(Principal principal) throws NotFoundException {
        Long id = userService.getIdByUsername(principal.getName());
        UserEntity userEntity = userService.getUserById(id);

        return ResponseEntity.ok(mapStructMapper.userEntityToUserGetDto(userEntity));
    }

    @GetMapping("/@{username}")
    public ResponseEntity<UserGetDto> getUserByUsername(@PathVariable String username) throws NotFoundException {
        Long id = userService.getIdByUsername(username);
        UserEntity userEntity = userService.getUserById(id);

        return ResponseEntity.ok(mapStructMapper.userEntityToUserGetDto(userEntity));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserGetDto> getUserById(@PathVariable Long userId) throws NotFoundException {
        UserEntity userEntity = userService.getUserById(userId);

        return ResponseEntity.ok(mapStructMapper.userEntityToUserGetDto(userEntity));
    }

    @PutMapping
    public ResponseEntity<UserGetDto> updateUser(Principal principal,
                                                 @RequestBody UserUpdateDto userUpdateDto) throws NotFoundException {
        Long id = userService.getIdByUsername(principal.getName());
        UserEntity userEntity = userService.updateUser(id, userUpdateDto);

        return ResponseEntity.ok(mapStructMapper.userEntityToUserGetDto(userEntity));
    }
}
