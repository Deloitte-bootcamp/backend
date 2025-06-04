package com.deloitte.bootcamp.api_backend.model.dto;

import com.deloitte.bootcamp.api_backend.model.entity.AgendamentoStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class AgendamentoDTO {
    private Long id;
    private Long clienteId;
    private Long profissionalId;
    private Long servicoId;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private AgendamentoStatus status;
}
