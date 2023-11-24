package com.hiper.agq.dao.impl;

import com.hiper.agq.dao.UserDAO;
import com.hiper.agq.entity.User;
import com.hiper.agq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * andre on 23/11/2023
 */
@Repository("userDAO")
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Override
    public List<User> selectAllUsers() {
        return null;
    }

    @Override
    public Optional<User> selectUserById(Long roleId) {
        return Optional.empty();
    }

    @Override
    public User insertUser(User user) {
        return null;
    }

    @Override
    public boolean existsUserWithId(Long id) {
        return false;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public User updateUser(User user) {
        return null;
    }
}
