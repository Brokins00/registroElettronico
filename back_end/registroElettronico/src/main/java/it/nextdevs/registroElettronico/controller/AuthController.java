package it.nextdevs.registroElettronico.controller;

import it.nextdevs.registroElettronico.dto.AuthDataDto;
import it.nextdevs.registroElettronico.dto.UtenteDto;
import it.nextdevs.registroElettronico.dto.UtenteLoginDto;
import it.nextdevs.registroElettronico.service.AuthService;
import it.nextdevs.registroElettronico.service.UtenteService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public Integer register(@RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).reduce("", (s,s2)->s+s2));
        }

        return utenteService.saveUtente(utenteDto);
    }

    @PostMapping("/auth/login")
    public AuthDataDto login(@RequestBody @Validated UtenteLoginDto utenteLoginDto, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).reduce("", (s,s2)->s+s2));
        }

        return authService.authenticateUtenteAndCreateToken(utenteLoginDto);
    }
}
