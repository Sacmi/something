package ru.sacmi.something.dto;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateDto {
    @NotNull
    @Length(min = 4, max = 16)
    String username;

    @NotNull
    @Length(min = 6)
    String password;

    @NotNull
    @Length(max = 12)
    String name;

    @NotNull
    @Email
    String email;
}
