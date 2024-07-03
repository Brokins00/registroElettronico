package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.MateriaDTO;
import it.nextdevs.registroElettronico.model.Materia;
import it.nextdevs.registroElettronico.service.MateriaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MateriaController {
    @Autowired
    private MateriaService materiaService;

    @GetMapping("/materie")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA', 'DOCENTE')")
    public List<Materia> getAllMaterie() {
        return materiaService.getAllMaterie();
    }

    @PostMapping("/materie")
    @PreAuthorize("hasAnyAuthority('SEGRETERIA')")
    public Integer saveMateria(@RequestBody @Validated MateriaDTO materiaDTO, BindingResult bindingResult)
            throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().
                    map(DefaultMessageSourceResolvable::getDefaultMessage).reduce("", (s1, s2) -> s1+s2));
        }
        return materiaService.saveMateria(materiaDTO);
    }
}
