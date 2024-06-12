package it.nextdevs.registroElettronico.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "indirizzi")
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String via;
    private String numeroCivico;
    private String citta;
    private String provincia;
    private String cap;
    @OneToMany(mappedBy = "indirizzo")
    @JsonIgnore
    List<Studente> studenti;
}
