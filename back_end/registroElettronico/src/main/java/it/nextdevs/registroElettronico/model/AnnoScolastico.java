package it.nextdevs.registroElettronico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Classe> classi;

    @ManyToMany
    @JoinTable(
            name = "studenti_anni",
            joinColumns = @JoinColumn(name="annoScolastico_id"),
            inverseJoinColumns = @JoinColumn(name="studente_id")
    )
    private List<Studente> studenti;

    @ManyToMany
    @JoinTable(
            name = "docenti_anni",
            joinColumns = @JoinColumn(name="annoScolastico_id"),
            inverseJoinColumns = @JoinColumn(name="docente_id")
    )
    private List<Docente> docenti;

    @OneToMany(mappedBy = "annoScolastico")
    @JsonManagedReference
    private List<Valutazione> valutazioni;
}
