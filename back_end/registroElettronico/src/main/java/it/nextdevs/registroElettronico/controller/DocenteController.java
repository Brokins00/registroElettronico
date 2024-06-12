package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.DocenteIndirizzoDto;
import it.nextdevs.registroElettronico.dto.DocenteSPIndirizzoDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.model.Docente;
import it.nextdevs.registroElettronico.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DocenteController {
    @Autowired
    private DocenteService docenteService;

    @GetMapping("/docenti")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public List<Docente> getAllStudenti() {
        return docenteService.getAllStudenti();
    }

    @PostMapping("/docenti")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Integer saveStudente(@RequestBody @Validated DocenteIndirizzoDto studenteIndirizzoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NonAutorizzatoException("Problemi");
        }
        return docenteService.salvaStudente(studenteIndirizzoDto);
    }

    @PutMapping("/docenti/{id}")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Docente updateStudente(@RequestBody @Validated DocenteSPIndirizzoDto studenteSPIndirizzoDto, BindingResult bindingResult, @PathVariable Integer id) {
        if (bindingResult.hasErrors()) {
            throw new NonAutorizzatoException("Problemi");
        }
        return docenteService.updateStudente(studenteSPIndirizzoDto, id);
    }
}
