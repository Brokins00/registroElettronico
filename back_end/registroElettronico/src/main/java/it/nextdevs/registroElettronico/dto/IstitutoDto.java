package it.nextdevs.registroElettronico.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IstitutoDto {
    @NotNull
    private String codiceUnivoco;
    @NotNull
    private String nome;

    public IstitutoDto(String codiceUnivoco, String nome) {
        this.codiceUnivoco = codiceUnivoco;
        this.nome = nome;
    }
}
