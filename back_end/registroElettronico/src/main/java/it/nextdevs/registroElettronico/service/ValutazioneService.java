package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.ValutazioneDto;
import it.nextdevs.registroElettronico.exception.UtenteNonTrovatoException;
import it.nextdevs.registroElettronico.model.*;
import it.nextdevs.registroElettronico.repository.DocenteRepository;
import it.nextdevs.registroElettronico.repository.MateriaRepository;
import it.nextdevs.registroElettronico.repository.StudenteRepository;
import it.nextdevs.registroElettronico.repository.ValutazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValutazioneService {
    @Autowired
    private ValutazioneRepository valutazioneRepository;
    @Autowired
    private AnnoScolasticoService annoScolasticoService;
    @Autowired
    private DocenteRepository docenteRepository;
    @Autowired
    private StudenteRepository studenteRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    public List<Valutazione> getAllValutazioni() {
        return valutazioneRepository.findAll();
    }

    public Integer saveValutazione(ValutazioneDto valutazioneDto) {
        Optional<AnnoScolastico> annoScolasticoOptional = annoScolasticoService.getAnnoById(valutazioneDto.getAnnoScolasticoId());
        if (annoScolasticoOptional.isPresent()) {
            Optional<Docente> docenteOptional = docenteRepository.findById(valutazioneDto.getDocenteId());
            if (docenteOptional.isPresent()) {
                Optional<Studente> studenteOptional = studenteRepository.findById(valutazioneDto.getStudenteId());
                if (studenteOptional.isPresent()) {
                    Optional<Materia> materiaOptional = materiaRepository.findById(valutazioneDto.getMateriaId());
                    if (materiaOptional.isPresent()) {
                        AnnoScolastico annoScolastico = annoScolasticoOptional.get();
                        Docente docente = docenteOptional.get();
                        Studente studente = studenteOptional.get();
                        Materia materia = materiaOptional.get();
                        Valutazione valutazione = new Valutazione();
                        valutazione.setDescrizione(valutazioneDto.getDescrizione());
                        valutazione.setMateria(materia);
                        valutazione.setDocente(docente);
                        valutazione.setStudente(studente);
                        valutazione.setVoto(valutazioneDto.getVoto());
                        valutazione.setData(valutazioneDto.getData());
                        valutazione.setTipologia(valutazioneDto.getTipologia());
                        valutazione.setAnnoScolastico(annoScolastico);
                        valutazioneRepository.save(valutazione);
                        return valutazione.getId();
                    } else {
                        throw new UtenteNonTrovatoException("Materia non trovata");
                    }
                } else {
                    throw new UtenteNonTrovatoException("Studente non trovato");
                }
            } else {
                throw new UtenteNonTrovatoException("Docente non trovato");
            }
        } else {
            throw new UtenteNonTrovatoException("Anno Scolastico non trovato");
        }
    }

    public List<Valutazione> findByStudenteAndAnno(Integer studenteId, Integer annoId) {
        return valutazioneRepository.findByStudente_IdAndAnnoScolastico_Id(studenteId, annoId);
    }
}
