package it.nextdevs.registroElettronico.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ValutazioneDto {
    private Double voto;
    private String tipologia;
    private String descrizione;
    private LocalDate data;

    private Integer annoScolasticoId;
    private Integer studenteId;
    private Integer docenteId;
    private Integer materiaId;
}
