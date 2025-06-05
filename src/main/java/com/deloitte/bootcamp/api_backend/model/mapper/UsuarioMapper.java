package com.deloitte.bootcamp.api_backend.model.mapper;

import com.deloitte.bootcamp.api_backend.model.dto.UserDTO;
import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
import com.deloitte.bootcamp.api_backend.model.entity.User;

public class UsuarioMapper {

    public static UserDTO toDTO(User usuario) {
        if (usuario == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setRoleName(String.valueOf(usuario.getRoleName()));    ; // Converte enum para String
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User usuario = new User();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setRoleName(RoleName.valueOf(dto.getRoleName())); // Converte String para enum
        return usuario;
    }
}
