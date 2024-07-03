package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.MateriaDTO;
import it.nextdevs.registroElettronico.exception.UtenteNonTrovatoException;
import it.nextdevs.registroElettronico.model.IndirizzoScuola;
import it.nextdevs.registroElettronico.model.Materia;
import it.nextdevs.registroElettronico.repository.IndirizzoScuolaRepository;
import it.nextdevs.registroElettronico.repository.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private IndirizzoScuolaRepository indirizzoScuolaRepository;

    public List<Materia> getAllMaterie() {
        return materiaRepository.findAll();
    }

    public Integer saveMateria(MateriaDTO materiaDto) {
        Optional<IndirizzoScuola> indirizzoOptional = indirizzoScuolaRepository.findById(materiaDto.getIndirizzoId());
        if (indirizzoOptional.isPresent()) {
            IndirizzoScuola indirizzoScuola = indirizzoOptional.get();
            Materia materia = new Materia();
            materia.setNome(materiaDto.getNome());
            materia = materiaRepository.save(materia);
            List<Materia> materie = indirizzoScuola.getMaterie();
            materie.add(materia);
            indirizzoScuola.setMaterie(materie);
            indirizzoScuolaRepository.save(indirizzoScuola);
            return materia.getId();
        } else {
            throw new UtenteNonTrovatoException("Indirizzo Scolastico non trovato");
        }
    }
}
