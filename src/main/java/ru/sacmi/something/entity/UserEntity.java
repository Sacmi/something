package ru.sacmi.something.entity;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

@Data
@Table(name = "user_entity")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    String email;

    @NotNull
    @Column(unique = true, nullable = false)
    String username;

    @NotNull
    @Column(nullable = false)
    String password;

    @NotNull
    @Column(nullable = false)
    String name;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Instant createdAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles;
}