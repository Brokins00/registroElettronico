package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.UtenteDto;
import it.nextdevs.registroElettronico.exception.UtenteNonTrovatoException;
import it.nextdevs.registroElettronico.model.Utente;
import it.nextdevs.registroElettronico.repository.UtenteRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Utente> getAllUtenti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return utenteRepository.findAll(pageable);
    }

    public Optional<Utente> getUtenteById(Integer id) {
        return utenteRepository.findById(id);
    }

    public Optional<Utente> getUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    public Optional<Utente> getUtenteByEmailAndIstitutoId(String email, String istitutoId) {
        return utenteRepository.findByEmailAndIstitutoCodiceUnivoco(email, istitutoId);
    }

    public Integer saveUtente(UtenteDto utenteDto) throws BadRequestException {
        if (getUtenteByEmail(utenteDto.getEmail()).isEmpty()) {
            Utente utente = new Utente();
            utente.setEmail(utenteDto.getEmail());
            utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
            utente.setRuoloUtente(utenteDto.getRuoloUtente());
            utenteRepository.save(utente);
            sendMail(utenteDto.getEmail());
            return utente.getId();
        } else {
            throw new BadRequestException("L'email dell'utente " + utenteDto.getEmail() + " già presente");
        }
    }

    public void sendMail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Iscrizione Registro Elettronico");
        message.setText("Buongiorno. Sei stato/a aggiunto/a al sistema di registro elettronico del tuo Istituto. Grazie per averci scelto!");

        javaMailSender.send(message);
    }

    public String deleteUtente(Integer id) {
        Optional<Utente> utenteOptional = getUtenteById(id);

        if (utenteOptional.isPresent()) {
            utenteRepository.delete(utenteOptional.get());
            return "Utente con id "+id+" eliminato con successo";
        } else {
            throw new UtenteNonTrovatoException("Utente con id "+id+" non trovato");
        }
    }
}
