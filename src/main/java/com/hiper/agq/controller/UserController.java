package com.hiper.agq.controller;

import com.hiper.agq.controller.common.ResponseHandler;
import com.hiper.agq.dto.UserDto;
import com.hiper.agq.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.hiper.agq.utils.Constants.API_BASE_PATH;

/**
 * andre on 23/11/2023
 */
@RestController
@RequestMapping(API_BASE_PATH + "users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseHandler.response(HttpStatus.OK, userService.getAllUsers(), true);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable("userId") UUID userId) {
        return ResponseHandler.response(HttpStatus.OK, userService.getUserById(userId), true);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserDto user) {
        return ResponseHandler.response(HttpStatus.OK, userService.createUser(user), true);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") UUID userId) {
        userService.deleteUserById(userId);
        return ResponseHandler.response(HttpStatus.OK, "User deleted successfully", true);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") UUID userId, @RequestBody UserDto user) {
        return ResponseHandler.response(HttpStatus.OK, userService.updateUser(userId, user), true);
    }
}
