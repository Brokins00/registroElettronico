package it.nextdevs.registroElettronico.dto;

import it.nextdevs.registroElettronico.enums.RuoloUtente;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudenteSPIndirizzoDto {
    @NotNull
    private String email;
    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @NotNull
    private LocalDate dataDiNascita;
    @NotNull
    private String codiceFiscale;
    @NotNull
    private String via;
    @NotNull
    private String numeroCivico;
    @NotNull
    private String citta;
    @NotNull
    private String provincia;
    @NotNull
    private String cap;
    @NotNull
    private String codiceIstituto;

    public StudenteSPIndirizzoDto(String email, String nome, String cognome, LocalDate dataDiNascita, String codiceFiscale, String via, String numeroCivico, String citta, String provincia, String cap, String codiceIstituto) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.codiceFiscale = codiceFiscale;
        this.via = via;
        this.numeroCivico = numeroCivico;
        this.citta = citta;
        this.provincia = provincia;
        this.cap = cap;
        this.codiceIstituto = codiceIstituto;
    }
}
