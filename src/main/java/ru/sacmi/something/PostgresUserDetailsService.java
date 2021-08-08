package ru.sacmi.something;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sacmi.something.entity.UserEntity;
import ru.sacmi.something.repository.UserRepository;

@AllArgsConstructor
@Service
public class PostgresUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository
                .findByUsername(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRoles());
    }
}
