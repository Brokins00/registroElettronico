package it.nextdevs.registroElettronico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="indirizzi_scuola")
public class IndirizzoScuola {
    @Id
    @GeneratedValue
    private Integer id;
    private String nome;

    @ManyToMany(mappedBy = "indirizzi")
    @JsonIgnore
    private List<Istituto> istituti;

    @OneToMany(mappedBy = "indirizzo")
    @JsonIgnore
    private List<Classe> classi;

    @ManyToMany
    @JoinTable(
            name= "materie_indirizzi",
            joinColumns = @JoinColumn(name="indirizzo_id"),
            inverseJoinColumns = @JoinColumn(name="materia_id")
    )
    private List<Materia> materie;
}
