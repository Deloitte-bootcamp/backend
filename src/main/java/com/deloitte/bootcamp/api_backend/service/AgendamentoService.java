package com.deloitte.bootcamp.api_backend.service;

import com.deloitte.bootcamp.api_backend.model.dto.AgendamentoDTO;
import com.deloitte.bootcamp.api_backend.model.entity.*;
import com.deloitte.bootcamp.api_backend.model.mapper.AgendamentoMapper;
import com.deloitte.bootcamp.api_backend.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private final UserRepository userRepository;
    private final ServicoRepository servicoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final DisponibilidadeRepository disponibilidadeRepository;
    private final UserServices userServices;

    // ================== GET METHODS ===================

    public List<User> listarProfissionais() {
        return userRepository.findByRoleName("PROFISSIONAL");
    }

    // Listar agendamentos do cliente
    public List<AgendamentoDTO> listarMeusAgendamentos() {
        var loggedUser = userServices.getLoggedUser();
        List<Agendamento> agendamentos = agendamentoRepository.findByClienteId(loggedUser.getId());
        List<AgendamentoDTO> dtos = new ArrayList<>();
        for (Agendamento ag : agendamentos) {
            dtos.add(AgendamentoMapper.toDto(ag));
        }
        return dtos;
    }

    // Listar agendamentos de um profissional em um dia
    public List<AgendamentoDTO> listarAgendamentosPorDia(Long profissionalId, LocalDate data) {
        List<Agendamento> agendamentos = agendamentoRepository.findByProfissionalIdAndData(profissionalId, data);
        List<AgendamentoDTO> dtos = new ArrayList<>();
        for (Agendamento ag : agendamentos) {
            dtos.add(AgendamentoMapper.toDto(ag));
        }
        return dtos;
    }

    // Listar todos agendamentos de um profissional
    public List<AgendamentoDTO> listarTodosAgendamentosProfissional(Long profissionalId) {
        List<Agendamento> agendamentos = agendamentoRepository.findByProfissionalId(profissionalId);
        List<AgendamentoDTO> dtos = new ArrayList<>();
        for (Agendamento ag : agendamentos) {
            dtos.add(AgendamentoMapper.toDto(ag));
        }
        return dtos;
    }

    // ================== POST METHODS ===================

    // Criar agendamento
    public AgendamentoDTO criarAgendamento(AgendamentoDTO dto) {
        var loggedUser = userServices.getLoggedUser();
        if (loggedUser.getRoleName() != RoleName.ROLE_CLIENTE) {
            throw new SecurityException("Apenas clientes podem agendar.");
        }

        User cliente = userServices.getLoggedUserEntity();
        User profissional = userRepository.findByEmail(dto.getProfissionalEmail()).orElseThrow();
        Servico servico = servicoRepository.findById(dto.getServicoId()).orElseThrow();

        DiaSemana diaSemana = DiaSemana.fromDayOfWeek(dto.getData().getDayOfWeek());
        List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByProfissionalIdAndDiaSemana(
                profissional.getId(), diaSemana
        );

        // 1. Valida se o horário está dentro da disponibilidade
        boolean dentroDaDisponibilidade = false;
        for (Disponibilidade d : disponibilidades) {
            if (!dto.getHoraInicio().isBefore(d.getHoraInicio()) && !dto.getHoraFim().isAfter(d.getHoraFim())) {
                dentroDaDisponibilidade = true;
                break;
            }
        }
        if (!dentroDaDisponibilidade) {
            throw new IllegalArgumentException("Horário fora da disponibilidade do profissional.");
        }

        // 2. Valida se há conflito com outros agendamentos
        List<Agendamento> agendados = agendamentoRepository.findByProfissionalIdAndData(profissional.getId(), dto.getData());
        for (Agendamento a : agendados) {
            if (!(dto.getHoraFim().isBefore(a.getHoraInicio()) || dto.getHoraInicio().isAfter(a.getHoraFim()))) {
                throw new IllegalArgumentException("Horário já ocupado.");
            }
        }

        // Força o id do cliente a ser o do logado (opcional, mas garante integridade)
        dto.setClienteId(cliente.getId());

        Agendamento agendamento = AgendamentoMapper.toEntity(dto, cliente, profissional, servico);
        agendamento.setStatus(AgendamentoStatus.AGENDADO);
        agendamento = agendamentoRepository.save(agendamento);
        return AgendamentoMapper.toDto(agendamento);
    }

    // ================== DELETE METHODS ===================

    // Cancelar agendamento - só pelo cliente autenticado
    public void cancelarAgendamento(Long agendamentoId) {
        var loggedUser = userServices.getLoggedUser();
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow();
        if (!agendamento.getCliente().getId().equals(loggedUser.getId())) {
            throw new SecurityException("Acesso negado. Só pode cancelar seu próprio agendamento.");
        }
        LocalDateTime inicio = LocalDateTime.of(agendamento.getData(), agendamento.getHoraInicio());
        if (inicio.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new IllegalArgumentException("Cancelamento permitido apenas com 2h de antecedência.");
        }
        agendamento.setStatus(AgendamentoStatus.CANCELADO_CLIENTE);
        agendamentoRepository.save(agendamento);
    }

}
