package com.deloitte.bootcamp.api_backend.controller;

import com.deloitte.bootcamp.api_backend.model.dto.UserResponseDTO;
import com.deloitte.bootcamp.api_backend.service.UserServices;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserServices userServices;

    @GetMapping("/me")
    public UserResponseDTO getLoggedUser() {
        return userServices.getLoggedUser();
    }
}
