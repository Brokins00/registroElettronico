package it.nextdevs.registroElettronico.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Festivita {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDate inizio;
    private LocalDate fine;
    private String descrizione;
    @ManyToOne
    @JoinColumn(name="anno_scolastico_id")
    private AnnoScolastico annoScolastico;
}
