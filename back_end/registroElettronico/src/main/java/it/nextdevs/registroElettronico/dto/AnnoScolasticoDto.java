package it.nextdevs.registroElettronico.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnnoScolasticoDto {
    @NotNull
    private LocalDate inizio;
    @NotNull
    private LocalDate fine;
    @NotNull
    private String codiceIstituto;
}
