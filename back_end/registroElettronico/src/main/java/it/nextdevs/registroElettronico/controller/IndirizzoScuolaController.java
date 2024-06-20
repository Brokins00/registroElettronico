package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.IndirizzoScuolaDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.model.IndirizzoScuola;
import it.nextdevs.registroElettronico.service.IndirizzoScuolaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IndirizzoScuolaController {
    @Autowired
    private IndirizzoScuolaService indirizzoScuolaService;

    @PostMapping("indirizzi-scolastici")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Integer saveIndirizzoScuola(@RequestBody @Validated IndirizzoScuolaDto indirizzoScuolaDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new NonAutorizzatoException("Problemi");
        }
        return indirizzoScuolaService.saveIndirizzoScuola(indirizzoScuolaDto);
    }

    @GetMapping("indirizzi-scolastici")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public List<IndirizzoScuola> getAllIndirizzi() {
        return indirizzoScuolaService.getAllIndirizzi();
    }
}
