package com.deloitte.bootcamp.api_backend.model.entity;

public enum DiaSemana {
    DOMINGO,
    SEGUNDA,
    TERCA,
    QUARTA,
    QUINTA,
    SEXTA,
    SABADO;

    public static DiaSemana fromDayOfWeek(java.time.DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY    -> SEGUNDA;
            case TUESDAY   -> TERCA;
            case WEDNESDAY -> QUARTA;
            case THURSDAY  -> QUINTA;
            case FRIDAY    -> SEXTA;
            case SATURDAY  -> SABADO;
            case SUNDAY    -> DOMINGO;
            default        -> throw new IllegalArgumentException("Dia da semana inválido: " + dayOfWeek);
        };
    }

}



