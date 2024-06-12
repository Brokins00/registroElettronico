package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.AuthDataDto;
import it.nextdevs.registroElettronico.dto.UtenteLoginDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.model.Utente;
import it.nextdevs.registroElettronico.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private IstitutoService istitutoService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthDataDto authenticateUtenteAndCreateToken(UtenteLoginDto utenteLoginDto) {
        Optional<Utente> utenteOptional = utenteService.getUtenteByEmailAndIstitutoId(utenteLoginDto.getEmail(),
                utenteLoginDto.getCodiceIstituto());

        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();

            if (passwordEncoder.matches(utenteLoginDto.getPassword(), utente.getPassword())) {
                AuthDataDto authDataDto = new AuthDataDto();
                authDataDto.setUser(utente);
                authDataDto.setAccessToken(jwtTool.createToken(utente));
                return authDataDto;
            } else {
                throw new NonAutorizzatoException("Password errata");
            }
        } else {
            throw new NonAutorizzatoException("Email con Istituto non trovata");
        }
    }
}
