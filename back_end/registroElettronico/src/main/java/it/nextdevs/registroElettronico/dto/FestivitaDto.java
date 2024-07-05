package it.nextdevs.registroElettronico.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FestivitaDto {
    @NotNull
    private LocalDate inizio;
    @NotNull
    private LocalDate fine;
    @NotNull
    private String descrizione;
    @NotNull
    private Integer annoScolastico;
}
