package com.deloitte.bootcamp.api_backend.model.dto;

import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    @Email
    private String email;
    private String nome;
    private RoleName roleName;
}
