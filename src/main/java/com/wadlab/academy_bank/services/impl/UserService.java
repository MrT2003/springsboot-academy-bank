package com.wadlab.academy_bank.services.impl;

import java.util.List;

import com.wadlab.academy_bank.dto.BankResponse;
import com.wadlab.academy_bank.dto.UserRequest;
import com.wadlab.academy_bank.dto.UserResponse;
import com.wadlab.academy_bank.dto.UserResponseDetail;

public interface UserService {
    public BankResponse createAccount(UserRequest userRequest);
    public List<UserResponse> getAllUsers() ;
    public UserResponseDetail getUserById(Long id) ;
    public UserResponseDetail updateUser(Long id, UserRequest userRequest);
    public void deleteUser(Long id);
}
