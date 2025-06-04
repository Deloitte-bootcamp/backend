package com.deloitte.bootcamp.api_backend.repository;

import com.deloitte.bootcamp.api_backend.model.entity.Disponibilidade;
import com.deloitte.bootcamp.api_backend.model.entity.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DisponibilidadeRepository extends JpaRepository<Disponibilidade, Long> {
    // Busca todas as disponibilidades de um profissional
    List<Disponibilidade> findByProfissionalId(Long profissionalId);

    // Busca as disponibilidades de um profissional para um dia da semana
    List<Disponibilidade> findByProfissionalIdAndDiaSemana(Long profissionalId, DiaSemana diaSemana);

    // Busca se existe uma disponibilidade exata (pouco usado, mas pode ser útil)
    boolean existsByProfissionalIdAndDiaSemanaAndHoraInicioAndHoraFim(
            Long profissionalId, DiaSemana diaSemana, java.time.LocalTime horaInicio, java.time.LocalTime horaFim
    );
}
