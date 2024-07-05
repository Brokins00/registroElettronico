package it.nextdevs.registroElettronico.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MateriaDTO {
    @NotNull
    private Integer indirizzoId;
    @NotNull
    private String nome;
}
