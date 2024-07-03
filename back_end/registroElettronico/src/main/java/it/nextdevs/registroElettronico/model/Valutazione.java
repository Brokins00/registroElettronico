package it.nextdevs.registroElettronico.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="valutazioni")
public class Valutazione {
    @Id
    @GeneratedValue
    private Integer id;
    private Double voto;
    private String tipologia;
    private String descrizione;
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "anno_scolastico_id")
    @JsonBackReference
    private AnnoScolastico annoScolastico;
    @ManyToOne
    @JoinColumn(name = "studente_id")
    @JsonBackReference
    private Studente studente;
    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;
    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;
}
