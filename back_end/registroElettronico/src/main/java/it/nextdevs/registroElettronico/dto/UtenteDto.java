package it.nextdevs.registroElettronico.dto;

import it.nextdevs.registroElettronico.enums.RuoloUtente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class UtenteDto {
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    private RuoloUtente ruoloUtente;

    public UtenteDto(String email, String password) {
        this.email = email;
        this.password = password;
        this.ruoloUtente = RuoloUtente.UTENTE;
    }
}
