package com.deloitte.bootcamp.api_backend.controller;

import com.deloitte.bootcamp.api_backend.model.dto.DisponibilidadeDTO;
import com.deloitte.bootcamp.api_backend.service.DisponibilidadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
@AllArgsConstructor
public class DisponibilidadeController {

    private final DisponibilidadeService disponibilidadeService;

    @GetMapping
    public List<DisponibilidadeDTO> findAll() {
        return disponibilidadeService.findAll();
    }

    @GetMapping("/profissional/{profissionalId}")
    public List<DisponibilidadeDTO> findByProfissionalId(@PathVariable Long profissionalId) {
        return disponibilidadeService.findByProfissionalId(profissionalId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<DisponibilidadeDTO> save(@RequestBody DisponibilidadeDTO dto) {
        return disponibilidadeService.save(dto);
    }

    @PutMapping("/{id}")
    public DisponibilidadeDTO update(@PathVariable Long id, @RequestBody DisponibilidadeDTO dto) {
        return disponibilidadeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        disponibilidadeService.delete(id);
    }
}