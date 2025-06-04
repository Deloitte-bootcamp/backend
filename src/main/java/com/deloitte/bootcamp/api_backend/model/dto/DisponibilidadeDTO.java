package com.deloitte.bootcamp.api_backend.model.dto;


import com.deloitte.bootcamp.api_backend.model.entity.DiaSemana;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class DisponibilidadeDTO {
    private Long id;
    private Long profissionalId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> diasSemana;

    private String diaSemana;
    private String horaInicio;
    private String horaFim;
}
