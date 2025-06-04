package com.deloitte.bootcamp.api_backend.service.register_login;

import com.deloitte.bootcamp.api_backend.model.dto.LoginResponseDTO;
import com.deloitte.bootcamp.api_backend.model.dto.UserDTO;
import com.deloitte.bootcamp.api_backend.model.entity.User;
import com.deloitte.bootcamp.api_backend.model.mapper.UsuarioMapper;
import com.deloitte.bootcamp.api_backend.repository.UserRepository;
import com.deloitte.bootcamp.api_backend.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDTO authenticateUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get());
            UserDTO userDTO = UsuarioMapper.toDTO(user.get());
            return new LoginResponseDTO(token, "login successful", userDTO);
        }
        return new LoginResponseDTO(null, "invalid credentials", null);
    }
}
