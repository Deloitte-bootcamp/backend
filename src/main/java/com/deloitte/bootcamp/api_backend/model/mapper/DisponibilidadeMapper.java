package com.deloitte.bootcamp.api_backend.model.mapper;

import com.deloitte.bootcamp.api_backend.model.dto.DisponibilidadeDTO;
import com.deloitte.bootcamp.api_backend.model.entity.Disponibilidade;
import com.deloitte.bootcamp.api_backend.model.entity.DiaSemana;
import com.deloitte.bootcamp.api_backend.model.entity.User;

import java.time.LocalTime;

public class DisponibilidadeMapper {

    public static DisponibilidadeDTO toDTO(Disponibilidade disponibilidade) {
        if (disponibilidade == null) return null;
        DisponibilidadeDTO dto = new DisponibilidadeDTO();
        dto.setId(disponibilidade.getId());
        dto.setProfissionalId(
                disponibilidade.getProfissional() != null ? disponibilidade.getProfissional().getId() : null
        );
        String diaSemana = disponibilidade.getDiaSemana() != null ? String.valueOf(disponibilidade.getDiaSemana()) : null;
        dto.setDiaSemana(diaSemana);
        dto.setDiasSemana(null); // Só usado no POST para múltiplos dias
        // Converte LocalTime para String
        dto.setHoraInicio(disponibilidade.getHoraInicio() != null ? disponibilidade.getHoraInicio().toString() : null);
        dto.setHoraFim(disponibilidade.getHoraFim() != null ? disponibilidade.getHoraFim().toString() : null);
        return dto;
    }

    public static Disponibilidade toEntity(DisponibilidadeDTO dto) {
        if (dto == null) return null;
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setId(dto.getId());
        if (dto.getProfissionalId() != null) {
            User profissional = new User();
            profissional.setId(dto.getProfissionalId());
            disponibilidade.setProfissional(profissional);
        } else {
            disponibilidade.setProfissional(null);
        }
        disponibilidade.setDiaSemana(
                dto.getDiaSemana() != null ? DiaSemana.valueOf(dto.getDiaSemana()) : null
        );
        // Converte String para LocalTime
        disponibilidade.setHoraInicio(dto.getHoraInicio() != null ? LocalTime.parse(dto.getHoraInicio()) : null);
        disponibilidade.setHoraFim(dto.getHoraFim() != null ? LocalTime.parse(dto.getHoraFim()) : null);
        return disponibilidade;
    }
}