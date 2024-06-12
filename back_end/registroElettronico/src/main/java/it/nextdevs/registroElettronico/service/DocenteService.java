package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.DocenteIndirizzoDto;
import it.nextdevs.registroElettronico.dto.DocenteSPIndirizzoDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.model.Indirizzo;
import it.nextdevs.registroElettronico.model.Istituto;
import it.nextdevs.registroElettronico.model.Docente;
import it.nextdevs.registroElettronico.model.Utente;
import it.nextdevs.registroElettronico.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private IstitutoRepository istitutoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Docente> getAllStudenti() {
        return docenteRepository.findAll();
    }

    public Integer salvaStudente(DocenteIndirizzoDto docenteIndirizzoDto) {
        Optional<Utente> utenteOptional = utenteRepository.findByEmail(docenteIndirizzoDto.getEmail());
        if (utenteOptional.isEmpty()) {
            Docente docente = new Docente();
            docente.setNome(docenteIndirizzoDto.getNome());
            docente.setCognome(docenteIndirizzoDto.getCognome());
            docente.setEmail(docenteIndirizzoDto.getEmail());
            docente.setRuoloUtente(docenteIndirizzoDto.getRuoloUtente());
            docente.setDataDiNascita(docenteIndirizzoDto.getDataDiNascita());
            docente.setPassword(passwordEncoder.encode(docenteIndirizzoDto.getPassword()));
            docente.setCodiceFiscale(docenteIndirizzoDto.getCodiceFiscale());
            Optional<Istituto> istitutoOptional = istitutoRepository.findById(docenteIndirizzoDto.getCodiceIstituto());
            istitutoOptional.ifPresent(docente::setIstituto);
            Optional<Indirizzo> indirizzoOptional = indirizzoRepository.findByViaAndNumeroCivicoAndCittaAndProvincia(
                    docenteIndirizzoDto.getVia(),
                    docenteIndirizzoDto.getNumeroCivico(),
                    docenteIndirizzoDto.getCitta(),
                    docenteIndirizzoDto.getProvincia()
            );
            Indirizzo indirizzo = getIndirizzo(docenteIndirizzoDto, indirizzoOptional);
            docente.setIndirizzo(indirizzo);
            docenteRepository.save(docente);
            return docente.getId();
        } else {
            throw new NonAutorizzatoException("Utente già esistente");
        }
    }

    public Docente updateStudente(DocenteSPIndirizzoDto studenteSPIndirizzoDto, Integer id) {
        Optional<Docente> studenteOptional = docenteRepository.findById(id);
        if (studenteOptional.isPresent()) {
            Docente docente = studenteOptional.get();
            docente.setNome(studenteSPIndirizzoDto.getNome());
            docente.setCognome(studenteSPIndirizzoDto.getCognome());
            docente.setEmail(studenteSPIndirizzoDto.getEmail());
            docente.setDataDiNascita(studenteSPIndirizzoDto.getDataDiNascita());
            docente.setCodiceFiscale(studenteSPIndirizzoDto.getCodiceFiscale());
            Optional<Istituto> istitutoOptional = istitutoRepository.findById(studenteSPIndirizzoDto.getCodiceIstituto());
            istitutoOptional.ifPresent(docente::setIstituto);
            Optional<Indirizzo> indirizzoOptional = indirizzoRepository.findByViaAndNumeroCivicoAndCittaAndProvincia(
                    studenteSPIndirizzoDto.getVia(),
                    studenteSPIndirizzoDto.getNumeroCivico(),
                    studenteSPIndirizzoDto.getCitta(),
                    studenteSPIndirizzoDto.getProvincia()
            );
            Indirizzo indirizzo = getIndirizzo(studenteSPIndirizzoDto, indirizzoOptional);
            docente.setIndirizzo(indirizzo);
            docenteRepository.save(docente);
            return docente;
        } else {
            throw new NonAutorizzatoException("Utente non esistente");
        }
    }

    private Indirizzo getIndirizzo(DocenteIndirizzoDto docenteIndirizzoDto, Optional<Indirizzo> indirizzoOptional) {
        Indirizzo indirizzo;
        if (indirizzoOptional.isPresent()) {
            indirizzo = indirizzoOptional.get();
        } else {
            indirizzo = new Indirizzo();
            indirizzo.setCap(docenteIndirizzoDto.getCap());
            indirizzo.setCitta(docenteIndirizzoDto.getCitta());
            indirizzo.setVia(docenteIndirizzoDto.getVia());
            indirizzo.setProvincia(docenteIndirizzoDto.getProvincia());
            indirizzo.setNumeroCivico(docenteIndirizzoDto.getNumeroCivico());
            indirizzo = indirizzoRepository.save(indirizzo);
        }
        return indirizzo;
    }

    private Indirizzo getIndirizzo(DocenteSPIndirizzoDto docenteIndirizzoDto, Optional<Indirizzo> indirizzoOptional) {
        Indirizzo indirizzo;
        if (indirizzoOptional.isPresent()) {
            indirizzo = indirizzoOptional.get();
        } else {
            indirizzo = new Indirizzo();
            indirizzo.setCap(docenteIndirizzoDto.getCap());
            indirizzo.setCitta(docenteIndirizzoDto.getCitta());
            indirizzo.setVia(docenteIndirizzoDto.getVia());
            indirizzo.setProvincia(docenteIndirizzoDto.getProvincia());
            indirizzo.setNumeroCivico(docenteIndirizzoDto.getNumeroCivico());
            indirizzo = indirizzoRepository.save(indirizzo);
        }
        return indirizzo;
    }
}
