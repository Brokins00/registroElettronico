package it.nextdevs.registroElettronico.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name="anni_scolastici")
public class AnnoScolastico {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDate inizio;
    private LocalDate fine;

    @OneToMany(mappedBy = "annoScolastico")
    private List<Festivita> festivita;

    @ManyToOne
    @JoinColumn(name="istituto_id")
    private Istituto istituto;

    @OneToMany(mappedBy = "annoScolastico")
    private List<Classe> classi;

    @ManyToMany
    @JoinTable(
            name = "studenti_anni",
            joinColumns = @JoinColumn(name="annoScolastico_id"),
            inverseJoinColumns = @JoinColumn(name="studente_id")
    )
    private List<Studente> studenti;
}
