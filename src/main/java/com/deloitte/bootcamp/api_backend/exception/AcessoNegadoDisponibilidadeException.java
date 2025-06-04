package com.deloitte.bootcamp.api_backend.exception;

public class AcessoNegadoDisponibilidadeException extends RuntimeException {
    public AcessoNegadoDisponibilidadeException(String message) {
        super(message);
    }
}