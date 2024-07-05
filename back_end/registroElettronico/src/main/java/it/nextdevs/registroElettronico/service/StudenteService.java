package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.StudenteIndirizzoDto;
import it.nextdevs.registroElettronico.dto.StudenteSPIndirizzoDto;
import it.nextdevs.registroElettronico.exception.NonAutorizzatoException;
import it.nextdevs.registroElettronico.model.Indirizzo;
import it.nextdevs.registroElettronico.model.Istituto;
import it.nextdevs.registroElettronico.model.Studente;
import it.nextdevs.registroElettronico.model.Utente;
import it.nextdevs.registroElettronico.repository.IndirizzoRepository;
import it.nextdevs.registroElettronico.repository.IstitutoRepository;
import it.nextdevs.registroElettronico.repository.StudenteRepository;
import it.nextdevs.registroElettronico.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudenteService {
    @Autowired
    private StudenteRepository studenteRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private IstitutoRepository istitutoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Studente> getAllStudenti() {
        return studenteRepository.findAll();
    }

    public Integer salvaStudente(StudenteIndirizzoDto studenteIndirizzoDto) {
        Optional<Utente> utenteOptional = utenteRepository.findByEmail(studenteIndirizzoDto.getEmail());
        if (utenteOptional.isEmpty()) {
            System.out.println(studenteIndirizzoDto);
            Studente studente = new Studente();
            System.out.println(studente.getId());
            studente.setNome(studenteIndirizzoDto.getNome());
            studente.setCognome(studenteIndirizzoDto.getCognome());
            studente.setEmail(studenteIndirizzoDto.getEmail());
            studente.setRuoloUtente(studenteIndirizzoDto.getRuoloUtente());
            studente.setDataDiNascita(studenteIndirizzoDto.getDataDiNascita());
            studente.setPassword(passwordEncoder.encode(studenteIndirizzoDto.getPassword()));
            studente.setCodiceFiscale(studenteIndirizzoDto.getCodiceFiscale());
            Optional<Istituto> istitutoOptional = istitutoRepository.findById(studenteIndirizzoDto.getCodiceIstituto());
            istitutoOptional.ifPresent(studente::setIstituto);
            Optional<Indirizzo> indirizzoOptional = indirizzoRepository.findByViaAndNumeroCivicoAndCittaAndProvincia(
                    studenteIndirizzoDto.getVia(),
                    studenteIndirizzoDto.getNumeroCivico(),
                    studenteIndirizzoDto.getCitta(),
                    studenteIndirizzoDto.getProvincia()
            );
            Indirizzo indirizzo = getIndirizzo(studenteIndirizzoDto, indirizzoOptional);
            studente.setIndirizzo(indirizzo);
            studenteRepository.save(studente);
            return studente.getId();
        } else {
            throw new NonAutorizzatoException("Utente già esistente");
        }
    }

    public Studente updateStudente(StudenteSPIndirizzoDto studenteSPIndirizzoDto, Integer id) {
        Optional<Studente> studenteOptional = studenteRepository.findById(id);
        if (studenteOptional.isPresent()) {
            Studente studente = studenteOptional.get();
            studente.setNome(studenteSPIndirizzoDto.getNome());
            studente.setCognome(studenteSPIndirizzoDto.getCognome());
            studente.setEmail(studenteSPIndirizzoDto.getEmail());
            studente.setDataDiNascita(studenteSPIndirizzoDto.getDataDiNascita());
            studente.setCodiceFiscale(studenteSPIndirizzoDto.getCodiceFiscale());
            Optional<Istituto> istitutoOptional = istitutoRepository.findById(studenteSPIndirizzoDto.getCodiceIstituto());
            istitutoOptional.ifPresent(studente::setIstituto);
            Optional<Indirizzo> indirizzoOptional = indirizzoRepository.findByViaAndNumeroCivicoAndCittaAndProvincia(
                    studenteSPIndirizzoDto.getVia(),
                    studenteSPIndirizzoDto.getNumeroCivico(),
                    studenteSPIndirizzoDto.getCitta(),
                    studenteSPIndirizzoDto.getProvincia()
            );
            Indirizzo indirizzo = getIndirizzo(studenteSPIndirizzoDto, indirizzoOptional);
            studente.setIndirizzo(indirizzo);
            studenteRepository.save(studente);
            return studente;
        } else {
            throw new NonAutorizzatoException("Utente non esistente");
        }
    }

    private Indirizzo getIndirizzo(StudenteIndirizzoDto studenteIndirizzoDto, Optional<Indirizzo> indirizzoOptional) {
        Indirizzo indirizzo;
        if (indirizzoOptional.isPresent()) {
            indirizzo = indirizzoOptional.get();
        } else {
            indirizzo = new Indirizzo();
            indirizzo.setCap(studenteIndirizzoDto.getCap());
            indirizzo.setCitta(studenteIndirizzoDto.getCitta());
            indirizzo.setVia(studenteIndirizzoDto.getVia());
            indirizzo.setProvincia(studenteIndirizzoDto.getProvincia());
            indirizzo.setNumeroCivico(studenteIndirizzoDto.getNumeroCivico());
            indirizzo = indirizzoRepository.save(indirizzo);
        }
        return indirizzo;
    }

    private Indirizzo getIndirizzo(StudenteSPIndirizzoDto studenteIndirizzoDto, Optional<Indirizzo> indirizzoOptional) {
        Indirizzo indirizzo;
        if (indirizzoOptional.isPresent()) {
            indirizzo = indirizzoOptional.get();
        } else {
            indirizzo = new Indirizzo();
            indirizzo.setCap(studenteIndirizzoDto.getCap());
            indirizzo.setCitta(studenteIndirizzoDto.getCitta());
            indirizzo.setVia(studenteIndirizzoDto.getVia());
            indirizzo.setProvincia(studenteIndirizzoDto.getProvincia());
            indirizzo.setNumeroCivico(studenteIndirizzoDto.getNumeroCivico());
            indirizzo = indirizzoRepository.save(indirizzo);
        }
        return indirizzo;
    }
}
