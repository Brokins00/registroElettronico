package it.nextdevs.registroElettronico.dto;

import it.nextdevs.registroElettronico.model.Studente;
import lombok.Data;

import java.util.List;

@Data
public class PatchAnnoStudenteDto {
    private List<Integer> studenti;
}
