package com.hiper.agq.dto;

import com.hiper.agq.entity.enums.TypeUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.hiper.agq.entity.User}
 */
public record UserDto(UUID id, String fullName, @NotNull @Email String email, @NotNull TypeUser typeUser, @NotNull String mobilePhone,
                      String profilePicture) implements Serializable {
}