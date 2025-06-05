package com.deloitte.bootcamp.api_backend.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDTO {
    private Long id;
    private String nome;
    private String email;
    private String roleName;
}
