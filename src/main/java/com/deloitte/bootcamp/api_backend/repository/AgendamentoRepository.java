package com.deloitte.bootcamp.api_backend.repository;

import com.deloitte.bootcamp.api_backend.model.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

    List<Agendamento> findByProfissionalId(Long profissionalId);
    // Verifica se já existe um agendamento no mesmo horário (conflito)
    boolean existsByProfissionalIdAndDataAndHoraInicioAndHoraFim(
            Long profissionalId, LocalDate data, LocalTime horaInicio, LocalTime horaFim
    );

    // Busca todos os agendamentos de um cliente
    List<Agendamento> findByClienteId(Long clienteId);

    // Busca todos os agendamentos de um profissional em uma data
    List<Agendamento> findByProfissionalIdAndData(Long profissionalId, LocalDate data);

    // Busca agendamentos de um profissional para um serviço específico em uma data (opcional, se precisar filtrar por serviço)
    List<Agendamento> findByProfissionalIdAndServicoIdAndData(Long profissionalId, Long servicoId, LocalDate data);
}