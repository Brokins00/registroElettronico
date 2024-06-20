package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.FestivitaDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.service.FestivitaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FestivitaController {
    @Autowired
    private FestivitaService festivitaService;

    @PostMapping("/festivita")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Integer saveFestivita(@RequestBody @Validated FestivitaDto festivitaDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new NonAutorizzatoException("Problemi");
        }
        return festivitaService.saveFestivita(festivitaDto);
    }
}
