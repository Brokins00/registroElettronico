package it.nextdevs.registroElettronico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

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
    @ManyToMany(mappedBy = "studenti")
    @JsonIgnore
    private List<AnnoScolastico> anniScolastici;
    @ManyToMany(mappedBy = "studenti")
    @JsonIgnore
    private List<Classe> classi;
}
