package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.ValutazioneDto;
import it.nextdevs.registroElettronico.model.Valutazione;
import it.nextdevs.registroElettronico.service.ValutazioneService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ValutazioneController {
    @Autowired
    private ValutazioneService valutazioneService;

    @GetMapping("/valutazioni")
    public List<Valutazione> getAllValutazioni() {
        return valutazioneService.getAllValutazioni();
    }

    @PostMapping("/valutazioni")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA', 'DOCENTE')")
    public Integer saveValutazione(@RequestBody @Validated ValutazioneDto valutazioneDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(err -> err.getDefaultMessage()).reduce("", (s,s2) -> s+s2));
        }
        return valutazioneService.saveValutazione(valutazioneDto);
    }

    @GetMapping("/studenti/{studenteId}/anno/{annoId}")
    public List<Valutazione> getValutazioniByStudenteAndAnno(@PathVariable Integer studenteId, @PathVariable Integer annoId) {
        return valutazioneService.findByStudenteAndAnno(studenteId, annoId);
    }
}
