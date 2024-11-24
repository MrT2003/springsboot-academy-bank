package com.wadlab.academy_bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wadlab.academy_bank.dto.BankResponse;
import com.wadlab.academy_bank.dto.UserRequest;
import com.wadlab.academy_bank.dto.UserResponse;
import com.wadlab.academy_bank.dto.UserResponseDetail;
import com.wadlab.academy_bank.services.impl.UserNotFoundException;
import com.wadlab.academy_bank.services.impl.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest) {
        return userService.createAccount(userRequest);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDetail getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDetail> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest userRequest) {
        try {
            UserResponseDetail updatedUser = userService.updateUser(id, userRequest);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build(); // 204 No Content nếu xóa thành công
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404 Not Found nếu không tìm thấy user
        }
    }
    
}
