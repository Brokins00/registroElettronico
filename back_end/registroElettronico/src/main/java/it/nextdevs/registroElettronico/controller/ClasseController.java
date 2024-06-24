package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.ClasseDto;
import it.nextdevs.registroElettronico.dto.PatchAnnoStudenteDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.model.Classe;
import it.nextdevs.registroElettronico.service.ClasseService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClasseController {
    @Autowired
    private ClasseService classeService;

    @GetMapping("/classi")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public List<Classe> getAllClassi() {
        return classeService.getAllClassi();
    }

    @PostMapping("/classi")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Integer saveClasse(@RequestBody @Validated ClasseDto classeDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new NonAutorizzatoException("Problemi");
        }
        return classeService.saveClasse(classeDto);
    }

    @PutMapping("/classi/{id}")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Classe updateClasse(@PathVariable Integer id, @RequestBody @Validated ClasseDto classeDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("",((s1, s2) -> s1+s2)));
        }
        return classeService.updateClasse(classeDto, id);
    }

    @PatchMapping("/classi/{id}/studenti")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Classe patchAnnoStudente(@PathVariable Integer id, @RequestBody PatchAnnoStudenteDto patchAnnoStudenteDto) throws BadRequestException {
        return classeService.patchClasseStudente(id, patchAnnoStudenteDto);
    }
}
