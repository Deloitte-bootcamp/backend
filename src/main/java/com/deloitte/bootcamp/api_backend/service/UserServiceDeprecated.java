//package com.deloitte.bootcamp.api_backend.service;
//
//import com.deloitte.bootcamp.api_backend.model.dto.UserDTO;
//import com.deloitte.bootcamp.api_backend.model.entity.RoleName;
//import com.deloitte.bootcamp.api_backend.model.entity.User;
//import com.deloitte.bootcamp.api_backend.model.mapper.UsuarioMapper;
//import com.deloitte.bootcamp.api_backend.repository.UserRepository;
//import com.deloitte.bootcamp.api_backend.exception.UsuarioNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    // ============================= GET METHODS =============================
//
//    public ResponseEntity<List<UserDTO>> exibirTodosUsuarios() {
//        List<User> usuarios = userRepository.findAll();
//        List<UserDTO> dtos = usuarios.stream()
//                .map(UsuarioMapper::toDTO)
//                .toList();
//        return ResponseEntity.ok(dtos);
//    }
//
//    public ResponseEntity<UserDTO> buscarPorId(Long id) {
//        User usuario = buscarUsuarioEntidadePorId(id);
//        UserDTO dto = UsuarioMapper.toDTO(usuario);
//        return ResponseEntity.ok(dto);
//        }
//
//    // ============================= POST METHODS ============================
//
//    public ResponseEntity<UserDTO> salvar(UserDTO usuarioDTO) {
//        User usuario = UsuarioMapper.toEntity(usuarioDTO);
//        validarUsuario(usuario);
//        User salvo = userRepository.save(usuario);
//        return ResponseEntity.ok(UsuarioMapper.toDTO(salvo));
//    }
//
//    // ============================= PUT METHODS =============================
//
//    public ResponseEntity<UserDTO> atualizar(Long id, UserDTO usuarioDTO) {
//        User usuarioExistente = buscarUsuarioEntidadePorId(id);
//        usuarioExistente.setNome(usuarioDTO.getNome());
//        usuarioExistente.setEmail(usuarioDTO.getEmail());
//        usuarioExistente.setPassword(usuarioDTO.getSenha());
//        usuarioExistente.setTipoUsuario(RoleName.valueOf(usuarioDTO.getTipoUsuario()));
//        User atualizado = userRepository.save(usuarioExistente);
//        return ResponseEntity.ok(UsuarioMapper.toDTO(atualizado));
//    }
//
//    // ============================= DELETE METHODS ===========================
//
//    public void deletarPorId(Long id) {
//        User usuario = buscarUsuarioEntidadePorId(id); // Garante que existe ou lança exceção com método auxiliar
//        userRepository.delete(usuario); // Remove o usuário do banco
//    }
//    // ============================= MÉTODOS AUXILIARES =======================
//
//        public User buscarUsuarioEntidadePorId(Long id) {
//            validarId(id, "Usuário");
//            return userRepository.findById(id)
//                    .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado"));
//        }
//
//
//    private void validarId(Long id, String entidade) {
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException(entidade + " ID inválido");
//        }
//    }
//
//    private void validarUsuario(User usuario) {
//        List<String> erros = new ArrayList<>();
//        if (usuario == null) {
//            throw new IllegalArgumentException("Dados do usuário não informados");
//        }
//        if (usuario.getNome() == null || usuario.getNome().isBlank()) {
//            erros.add("Nome do usuário é obrigatório");
//        }
//        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
//            erros.add("Email do usuário é obrigatório");
//        }
//        if (usuario.getTipoUsuario() == null) {
//            erros.add("Tipo de usuário é obrigatório");
//        }
//        if (!erros.isEmpty()) {
//            throw new IllegalArgumentException(String.join("; ", erros));
//        }
//    }
//}