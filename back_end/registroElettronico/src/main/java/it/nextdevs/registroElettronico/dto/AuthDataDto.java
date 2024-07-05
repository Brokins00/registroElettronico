package it.nextdevs.registroElettronico.dto;

import it.nextdevs.registroElettronico.model.Utente;
import lombok.Data;

@Data
public class AuthDataDto {
    private String accessToken;
    private Utente user;
}
