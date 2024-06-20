package it.nextdevs.registroElettronico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="istituti")
public class Istituto {
    @Id
    private String codiceUnivoco;
    private String nome;

    @OneToMany(mappedBy = "istituto")
    @JsonIgnore
    private List<Utente> utenti;

    @OneToMany(mappedBy = "istituto")
    @JsonIgnore
    private List<AnnoScolastico> anniScolastici;

    @OneToMany(mappedBy = "istituto")
    @JsonIgnore
    private List<Classe> classi;

    @ManyToMany
    @JoinTable(
            name= "indirizzi_istituto",
            joinColumns = @JoinColumn(name="istituto_id"),
            inverseJoinColumns = @JoinColumn(name="indirizzo_id")
    )
    private List<IndirizzoScuola> indirizzi;
}
