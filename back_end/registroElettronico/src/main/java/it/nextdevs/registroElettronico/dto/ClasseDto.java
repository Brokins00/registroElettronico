package it.nextdevs.registroElettronico.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClasseDto {
    @NotNull
    private String nome;
    @NotNull
    private Integer indirizzo;
    @NotNull
    private String codiceIstituto;
    @NotNull
    private Integer annoScolastico;
}
