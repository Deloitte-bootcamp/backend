package com.deloitte.bootcamp.api_backend.exception;

    public class DisponibilidadeNotFoundException extends RuntimeException {
        public DisponibilidadeNotFoundException(String message) {
            super(message);
        }
    }