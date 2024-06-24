package it.nextdevs.registroElettronico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="classi")
public class Classe {
    @Id
    @GeneratedValue
    private Integer id;
    private String nome;

    @ManyToOne
    @JoinColumn(name="indirizzo_id")
    private IndirizzoScuola indirizzo;

    @ManyToOne
    @JoinColumn(name="istituto_id")
    @JsonIgnore
    private Istituto istituto;

    @ManyToOne
    @JoinColumn(name="anno_id")
    @JsonIgnore
    private AnnoScolastico annoScolastico;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name="studente_id"),
            inverseJoinColumns = @JoinColumn(name="classe_id"),
            name="classi_studenti"
    )
    private List<Studente> studenti;
}
