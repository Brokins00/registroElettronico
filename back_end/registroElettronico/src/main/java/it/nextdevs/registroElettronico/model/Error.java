package it.nextdevs.registroElettronico.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class Error {
    private String message;
    private LocalDateTime dataErrore;
    private HttpStatus statoErrore;
}
