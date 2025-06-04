package com.deloitte.bootcamp.api_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUsuarioNotFound(UsuarioNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ServicoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleServicoNotFound(ServicoNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DisponibilidadeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDisponibilidadeNotFound(DisponibilidadeNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(AcessoNegadoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAcessoNegado(AcessoNegadoException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(AcessoNegadoDisponibilidadeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleAcessoNegadoDisponibilidade(AcessoNegadoDisponibilidadeException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(DisponibilidadeSobrepostaException.class)
    public ResponseEntity<String> handleDisponibilidadeSobreposta(DisponibilidadeSobrepostaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}