package ru.sacmi.something.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateDto {
    @Length(min = 6)
    String password;

    @Length(max = 12)
    String name;

    @Email
    String email;
}
