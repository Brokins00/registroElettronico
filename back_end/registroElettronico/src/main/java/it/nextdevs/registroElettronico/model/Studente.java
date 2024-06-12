package it.nextdevs.registroElettronico.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name="studenti")
public class Studente extends Utente {
    private String nome;
    private String cognome;
    private LocalDate dataDiNascita;
    private String codiceFiscale;
    @ManyToOne
    @JoinColumn(name = "indirizzo_id")
    private Indirizzo indirizzo;
}
