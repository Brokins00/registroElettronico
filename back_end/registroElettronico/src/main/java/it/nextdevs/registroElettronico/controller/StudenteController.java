package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.StudenteIndirizzoDto;
import it.nextdevs.registroElettronico.dto.StudenteSPIndirizzoDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.model.Studente;
import it.nextdevs.registroElettronico.service.StudenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudenteController {
    @Autowired
    private StudenteService studenteService;

    @GetMapping("/studenti")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public List<Studente> getAllStudenti() {
        return studenteService.getAllStudenti();
    }

    @PostMapping("/studenti")
   @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Integer saveStudente(@RequestBody @Validated StudenteIndirizzoDto studenteIndirizzoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NonAutorizzatoException("Problemi");
        }
        return studenteService.salvaStudente(studenteIndirizzoDto);
    }

    @PutMapping("/studenti/{id}")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Studente updateStudente(@RequestBody @Validated StudenteSPIndirizzoDto studenteSPIndirizzoDto, BindingResult bindingResult, @PathVariable Integer id) {
        if (bindingResult.hasErrors()) {
            throw new NonAutorizzatoException("Problemi");
        }
        return studenteService.updateStudente(studenteSPIndirizzoDto, id);
    }
}
