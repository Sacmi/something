package ru.sacmi.something.service;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sacmi.something.dto.UserCreateDto;
import ru.sacmi.something.dto.UserUpdateDto;
import ru.sacmi.something.entity.UserEntity;
import ru.sacmi.something.exception.NotFoundException;
import ru.sacmi.something.mapper.MapStructMapper;
import ru.sacmi.something.repository.RoleRepository;
import ru.sacmi.something.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Collections;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@CacheConfig(cacheNames = "users")
@Transactional
@Service
public class UserService {
    UserRepository userRepository;
    MapStructMapper mapStructMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    private static final String DEFAULT_ROLE = "USER";

    public UserEntity createUser(UserCreateDto userCreateDto) {
        return createUser(userCreateDto, DEFAULT_ROLE);
    }

    public UserEntity createUser(UserCreateDto userCreateDto, String role) {
        UserEntity userEntity = UserEntity.builder()
                .username(userCreateDto.getUsername().toLowerCase().trim())
                .name(userCreateDto.getName().trim())
                .email(userCreateDto.getEmail().toLowerCase().trim())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .roles(Collections.singleton(roleRepository.findByName(role).orElseThrow()))
                .createdAt(Instant.now())
                .build();

        save(userEntity);

        return userEntity;
    }

    @CachePut(key = "#userEntity.id")
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Cacheable(key = "#username")
    public Long getIdByUsername(@NotNull String username) throws NotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Такой пользователь не найден."))
                .getId();
    }

    @Cacheable(key = "#id")
    public UserEntity getUserById(@NotNull Long id) throws NotFoundException {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Такой пользователь не найден."));
    }

    @CachePut(key = "#id")
    public UserEntity updateUser(Long id, UserUpdateDto userUpdateDto) throws NotFoundException {
        UserEntity userEntity = getUserById(id);

        return mapStructMapper.updateUserEntityFromDto(userUpdateDto, userEntity);
    }
}
