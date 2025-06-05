package com.deloitte.bootcamp.api_backend.model.dto;

import lombok.Data;

@Data
public class ServicoDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private Integer duracao;
    private Long profissionalId;
}
