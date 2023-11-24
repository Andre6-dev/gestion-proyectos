package com.hiper.agq.dto;

import com.hiper.agq.entity.enums.TypeUser;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.hiper.agq.entity.User}
 */
public record UserDto(UUID id, String fullName, String email, TypeUser typeUser, String mobilePhone,
                      String profilePicture) implements Serializable {
}