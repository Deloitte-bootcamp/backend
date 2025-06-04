package com.deloitte.bootcamp.api_backend.service;

import com.deloitte.bootcamp.api_backend.model.dto.UserResponseDTO;
import com.deloitte.bootcamp.api_backend.model.entity.User;
import com.deloitte.bootcamp.api_backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServices {

    private final UserRepository userRepository;

    public UserResponseDTO getLoggedUser() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            var user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
            UserResponseDTO dto = new UserResponseDTO();
            dto.setId(user.getId());
            dto.setNome(user.getNome());
            dto.setEmail(user.getEmail());
            dto.setRoleName(user.getRoleName());

            return dto;
        } catch (org.springframework.security.access.AccessDeniedException ex) {
            throw new RuntimeException("Acesso negado: usuário não autorizado (403)", ex);
        }
    }


    public User getLoggedUserEntity() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        } catch (org.springframework.security.access.AccessDeniedException ex) {
            throw new RuntimeException("Acesso negado: usuário não autorizado (403)", ex);
        }
    }


    public User buscarUsuarioEntidadePorId(Long usuarioId) {
        return userRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + usuarioId));
    }
}