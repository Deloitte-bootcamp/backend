package com.deloitte.bootcamp.api_backend.controller;

import com.deloitte.bootcamp.api_backend.model.dto.UserRegisterDTO;
import com.deloitte.bootcamp.api_backend.service.register_login.RegisterService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegisterDTO body) {
        System.out.println("[DEBUG] Recebido registro: " + body.getEmail());
        try {
            registerService.registerUser(body.getEmail(), body.getNome(), body.getPassword(), body.getRoleName());
            return ResponseEntity.ok(Collections.singletonMap("message", "Registered user"));
        }
        catch (IllegalArgumentException e) {
            log.warn("Erro ao registrar email de usuário", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
