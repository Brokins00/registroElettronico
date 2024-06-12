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
}
