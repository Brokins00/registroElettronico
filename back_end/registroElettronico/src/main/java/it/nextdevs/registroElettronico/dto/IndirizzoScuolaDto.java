package it.nextdevs.registroElettronico.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IndirizzoScuolaDto {
    @NotNull
    private String nomeIndirizzo;
    @NotNull
    private String codiceIstituto;
}
