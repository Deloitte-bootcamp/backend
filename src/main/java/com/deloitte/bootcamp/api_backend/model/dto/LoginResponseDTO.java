package com.deloitte.bootcamp.api_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String message;
    private UserDTO user;
}
