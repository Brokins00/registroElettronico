package it.nextdevs.registroElettronico.dto;

import it.nextdevs.registroElettronico.enums.RuoloUtente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UtenteLoginDto {
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String codiceIstituto;

    public UtenteLoginDto(String email, String password, String codiceIstituto) {
        this.email = email;
        this.password = password;
        this.codiceIstituto = codiceIstituto;
    }
}
