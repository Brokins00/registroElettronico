package it.nextdevs.registroElettronico.security;

import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.exception.UtenteNonTrovatoException;
import it.nextdevs.registroElettronico.model.Utente;
import it.nextdevs.registroElettronico.service.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new NonAutorizzatoException("La richiesta non ha un token. Fai di nuovo il login!");
        }

        String token = authHeader.substring(7);
        jwtTool.verifyToken(token);
        int id = jwtTool.getIdFromUtente(token);

        Optional<Utente> utenteOptional = utenteService.getUtenteById(id);

        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            Authentication authentication = new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new UtenteNonTrovatoException("Dipendente non trovato nel token. Fai di nuovo il login!");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/auth/login", request.getServletPath());
    }
}
