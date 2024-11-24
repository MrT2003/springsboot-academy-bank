package com.wadlab.academy_bank.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;  // ID của người dùng
    private String name; // Tên đầy đủ của người dùng
}
