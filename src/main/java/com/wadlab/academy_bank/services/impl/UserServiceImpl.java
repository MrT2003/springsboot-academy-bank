package com.wadlab.academy_bank.services.impl;

import com.wadlab.academy_bank.dto.AccountInfo;
import com.wadlab.academy_bank.dto.BankResponse;
import com.wadlab.academy_bank.dto.UserRequest;
import com.wadlab.academy_bank.dto.UserResponse;
import com.wadlab.academy_bank.dto.UserResponseDetail;
import com.wadlab.academy_bank.entity.User;
import com.wadlab.academy_bank.repository.UserRepository;
import com.wadlab.academy_bank.utils.AccountUtilis;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public BankResponse createAccount(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return BankResponse.builder()
                    .responeCode(AccountUtilis.ACCOUNT_EXISTS_CODE)
                    .responeCode(AccountUtilis.ACCOUNT_EXISTS_MESSAGE)
                    .AccountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtilis.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .email(userRequest.getEmail())
                .status("ACTIVE")
                .build();

        User savedUser = userRepository.save(newUser);
        AccountInfo accountInfo = AccountInfo.builder()
                .accountBalance(savedUser.getAccountBalance())
                .accountNumber(savedUser.getAccountNumber())
                .accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
                .build();

        return BankResponse.builder()
                .responeCode(AccountUtilis.ACCOUNT_CREATION_SUCCESS_CODE)
                .responeMessage(AccountUtilis.ACCOUNT_CREATION_SUCCESS_MASSAGE)
                .AccountInfo(accountInfo)
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll(); // Fetch all users from the database
        return users.stream()
                .map(user -> new UserResponse(user.getId(), user.getFirstName()))
                .collect(Collectors.toList()); // Map User entities to UserResponse DTOs
    }

    @Override
    public UserResponseDetail getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return UserResponseDetail.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .otherName(user.getOtherName())
                    .gender(user.getGender())
                    .address(user.getAddress())
                    .stateOfOrigin(user.getStateOfOrigin())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .alternativePhoneNumber(user.getAlternativePhoneNumber())
                    .accountNumber(user.getAccountNumber())
                    .accountBalance(user.getAccountBalance().toString())
                    .build();
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public UserResponseDetail updateUser(Long id, UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Cập nhật thông tin người dùng
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setOtherName(userRequest.getOtherName());
            user.setGender(userRequest.getGender());
            user.setAddress(userRequest.getAddress());
            user.setStateOfOrigin(userRequest.getStateOfOrigin());
            user.setEmail(userRequest.getEmail());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setAlternativePhoneNumber(userRequest.getAlternativePhoneNumber());
            user.setStatus(userRequest.getStatus());

            User updatedUser = userRepository.save(user);

            // Trả về thông tin chi tiết sau khi cập nhật
            return UserResponseDetail.builder()
                    .id(updatedUser.getId())
                    .firstName(updatedUser.getFirstName())
                    .lastName(updatedUser.getLastName())
                    .otherName(updatedUser.getOtherName())
                    .gender(updatedUser.getGender())
                    .address(updatedUser.getAddress())
                    .stateOfOrigin(updatedUser.getStateOfOrigin())
                    .email(updatedUser.getEmail())
                    .phoneNumber(updatedUser.getPhoneNumber())
                    .alternativePhoneNumber(updatedUser.getAlternativePhoneNumber())
                    .accountNumber(updatedUser.getAccountNumber())
                    .accountBalance(updatedUser.getAccountBalance().toString())
                    .build();
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
    }

}
