package com.hiper.agq.dao;

import com.hiper.agq.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    List<User> selectAllUsers();

    Optional<User> selectUserById(Long roleId);

    User insertUser(User user);

    boolean existsUserWithId(Long id);

    void deleteUserById(Long id);

    User updateUser(User user);
}
