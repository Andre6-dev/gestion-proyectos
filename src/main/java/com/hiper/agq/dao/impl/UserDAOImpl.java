package com.hiper.agq.dao.impl;

import com.hiper.agq.dao.UserDAO;
import com.hiper.agq.entity.User;
import com.hiper.agq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * andre on 23/11/2023
 */
@Repository("userDAO")
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Override
    public List<User> selectAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> selectUserById(UUID roleId) {
        return userRepository.findById(roleId);
    }

    @Override
    public List<User> selectUserByTaskId(UUID taskId) {
        return userRepository.findAllByTasksId(taskId);
    }

    @Override
    public User insertUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsUserWithId(UUID id) {
        return userRepository.existsUserById(id);
    }

    @Override
    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}
