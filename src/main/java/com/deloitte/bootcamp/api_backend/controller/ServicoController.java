package com.deloitte.bootcamp.api_backend.controller;

import com.deloitte.bootcamp.api_backend.model.dto.ServicoDTO;
import com.deloitte.bootcamp.api_backend.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    // ============================= GET METHODS =============================

    @GetMapping("/profissional/{usuarioId}")
    public ResponseEntity<List<ServicoDTO>> listarPorProfissional(@PathVariable Long usuarioId) {
        return servicoService.listarServicosPorProfissional(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoDTO> buscarPorId(@PathVariable Long id) {
        return servicoService.buscarPorId(id);
    }

    // ============================= POST METHODS ============================

    @PostMapping("/profissional/{usuarioId}")
    public ResponseEntity<ServicoDTO> criar(@PathVariable Long usuarioId, @RequestBody ServicoDTO dto) {
        System.out.println("Chegou aqui");
        return servicoService.criarServico(usuarioId, dto);
    }

    // ============================= PUT METHODS =============================

    @PutMapping("/{id}/profissional/{usuarioId}")
    public ResponseEntity<ServicoDTO> atualizar(@PathVariable Long id, @PathVariable Long usuarioId, @RequestBody ServicoDTO dto) {
        ServicoDTO atualizado = servicoService.atualizarServico(id, usuarioId, dto);
        return ResponseEntity.ok(atualizado);
    }

    // ============================= DELETE METHODS ===========================

    @DeleteMapping("/{id}/profissional/{usuarioId}")
    public ResponseEntity<String> deletar(@PathVariable Long id, @PathVariable Long usuarioId) {
        servicoService.deletarServico(id, usuarioId);
        return ResponseEntity.ok("Serviço excluído com sucesso");
    }
}