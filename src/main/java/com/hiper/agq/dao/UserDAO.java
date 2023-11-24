package com.hiper.agq.dao;

import com.hiper.agq.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDAO {

    List<User> selectAllUsers();

    Optional<User> selectUserById(UUID roleId);

    User insertUser(User user);

    boolean existsUserWithId(UUID id);

    void deleteUserById(UUID id);

    User updateUser(User user);
}
