package it.nextdevs.registroElettronico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="materie")
public class Materia {
    @Id
    @GeneratedValue
    private Integer id;
    private String nome;
    @ManyToMany(mappedBy = "materie")
    @JsonIgnore
    private List<IndirizzoScuola> indirizzi;
    @OneToMany(mappedBy = "materia")
    @JsonIgnore
    private List<Valutazione> valutazioni;
}
