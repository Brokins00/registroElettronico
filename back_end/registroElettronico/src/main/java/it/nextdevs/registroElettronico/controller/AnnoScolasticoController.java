package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.AnnoScolasticoDto;
import it.nextdevs.registroElettronico.dto.DocenteIndirizzoDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.model.AnnoScolastico;
import it.nextdevs.registroElettronico.model.Docente;
import it.nextdevs.registroElettronico.service.AnnoScolasticoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnnoScolasticoController {
    @Autowired
    private AnnoScolasticoService annoScolasticoService;

    @GetMapping("/anni-scolastici")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public List<AnnoScolastico> getAllAnni() {
        return annoScolasticoService.getAllAnni();
    }

    @PostMapping("/anni-scolastici")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Integer saveStudente(@RequestBody @Validated AnnoScolasticoDto annoScolasticoDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new NonAutorizzatoException("Problemi");
        }
        return annoScolasticoService.saveAnno(annoScolasticoDto);
    }

    @DeleteMapping("/anni-scolastici/{id}")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public String deleteAnno(@PathVariable Integer id) throws BadRequestException {
        return annoScolasticoService.deleteAnno(id);
    }
}
