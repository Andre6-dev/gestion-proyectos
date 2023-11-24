package com.hiper.agq.service;

import com.hiper.agq.dao.UserDAO;
import com.hiper.agq.dto.UserDto;
import com.hiper.agq.dto.mapper.UserMapper;
import com.hiper.agq.entity.User;
import com.hiper.agq.entity.enums.TypeUser;
import com.hiper.agq.exception.common.RequestValidationException;
import com.hiper.agq.exception.common.ResourceNotFoundException;
import com.hiper.agq.utils.UpdateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * andre on 23/11/2023
 */
@Slf4j(topic = "UserService")
@Service
public class UserService {

    private final UserDAO userDAO;

    private final UserMapper userMapper;

    public UserService(@Qualifier("userDAO") UserDAO userDAO, UserMapper userMapper) {
        this.userDAO = userDAO;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAllUsers() {
        log.info("Getting all users");
        return userDAO.selectAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserDto getUserById(UUID userId) {
        log.info("Getting user with id [{}]", userId);
        return this.userDAO.selectUserById(userId)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(userId)));
    }

    public UserDto createUser(UserDto userDto) {
        log.info("Creating user with username [{}]", userDto.fullName());
        if (userDAO.existsUserWithId(userDto.id())) {
            throw new IllegalStateException("User with username [%s] already exists".formatted(userDto.fullName()));
        }

        User newUser = userDAO.insertUser(userMapper.toEntity(userDto));

        return userMapper.toDto(newUser);
    }

    public void deleteUserById(UUID userId) {
        log.info("Deleting user with id [{}]", userId);
        if (!userDAO.existsUserWithId(userId)) {
            throw new ResourceNotFoundException("User with id [%s] not found".formatted(userId));
        }
        userDAO.deleteUserById(userId);
    }

    public UserDto updateUser(UUID id, UserDto userDto) {
        log.info("Updating user with id [{}]", userDto.id());

        User user = userDAO.selectUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id [%s] not found".formatted(id)));

        boolean changes =
                UpdateHelper.updateField(userDto.fullName(), user.getFullName(),user::setFullName) ||
                UpdateHelper.updateField(userDto.email(), user.getEmail(),user::setEmail) ||
                UpdateHelper.updateEnumField(Objects.requireNonNull(userDto.typeUser()).toString(), user.getTypeUser(), TypeUser::valueOf, user::setTypeUser) ||
                UpdateHelper.updateField(userDto.mobilePhone(), user.getMobilePhone(),user::setMobilePhone) ||
                UpdateHelper.updateField(userDto.profilePicture(), user.getProfilePicture(),user::setProfilePicture);


        if (!changes) {
            throw new RequestValidationException("No changes were made");
        }

        return userMapper.toDto(userDAO.updateUser(user));
    }

}
