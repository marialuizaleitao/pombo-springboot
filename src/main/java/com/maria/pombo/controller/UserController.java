package com.maria.pombo.controller;

import com.maria.pombo.exception.UserNotFoundException;
import com.maria.pombo.model.entity.User;
import com.maria.pombo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.listUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        if (updatedUser == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{userId}/toggle-admin")
    public ResponseEntity<Map<String, String>> toggleAdmin(@PathVariable String userId) {
        userService.toggleAdmin(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Admin status toggled successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}/like/{messageId}")
    public ResponseEntity<Map<String, String>> toggleLike(@PathVariable String userId, @PathVariable String messageId) {
        userService.toggleLike(userId, messageId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Like toggled successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}/report/{messageId}")
    public ResponseEntity<Map<String, String>> reportMessage(@PathVariable String userId, @PathVariable String messageId) {
        String reportMessage = userService.reportMessage(userId, messageId);
        Map<String, String> response = new HashMap<>();
        response.put("message", reportMessage);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
