package com.deloitte.bootcamp.api_backend.controller;

import com.deloitte.bootcamp.api_backend.model.dto.AgendamentoDTO;
import com.deloitte.bootcamp.api_backend.service.AgendamentoService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    // ================== GET ENDPOINTS ===================

    // Listar todos agendamentos de um profissional
    @GetMapping("/profissional/{profissionalId}")
    public List<AgendamentoDTO> listarTodosAgendamentosProfissional(@PathVariable Long profissionalId) {
        return agendamentoService.listarTodosAgendamentosProfissional(profissionalId);
    }

    // Listar agendamentos de um profissional em uma data específica
    @GetMapping("/profissional/{profissionalId}/data/{data}")
    public List<AgendamentoDTO> listarAgendamentosPorDia(
            @PathVariable Long profissionalId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return agendamentoService.listarAgendamentosPorDia(profissionalId, data);
    }

    // Listar agendamentos do cliente autenticado
    @GetMapping("/meus")
    public List<AgendamentoDTO> listarMeusAgendamentos() {
        return agendamentoService.listarMeusAgendamentos();
    }

    // ================== POST ENDPOINT ===================

    // Criar agendamento
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgendamentoDTO criarAgendamento(@RequestBody AgendamentoDTO dto) {
        return agendamentoService.criarAgendamento(dto);
    }

    // ================== DELETE ENDPOINT ===================

    // Cancelar agendamento
    @DeleteMapping("/{agendamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarAgendamento(@PathVariable Long agendamentoId) {
        agendamentoService.cancelarAgendamento(agendamentoId);
    }


}
