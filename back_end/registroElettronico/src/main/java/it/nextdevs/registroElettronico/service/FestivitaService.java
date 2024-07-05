package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.FestivitaDto;
import it.nextdevs.registroElettronico.model.AnnoScolastico;
import it.nextdevs.registroElettronico.model.Festivita;
import it.nextdevs.registroElettronico.repository.FestivitaRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FestivitaService {
    @Autowired
    private FestivitaRepository festivitaRepository;
    @Autowired
    private AnnoScolasticoService annoScolasticoService;

    public List<Festivita> getAllFestivitaByYear(Integer anno) {
        List<Festivita> festivitas = festivitaRepository.findAll();
        return festivitas.stream().filter(festivita -> festivita.getInizio().getYear() == anno).toList();
    }

    public Optional<Festivita> getFestivitaById(Integer id) {
        return festivitaRepository.findById(id);
    }

    public Integer saveFestivita(FestivitaDto festivitaDto) throws BadRequestException {
        Optional<AnnoScolastico> annoScolasticoOptional = annoScolasticoService.getAnnoById(festivitaDto.getAnnoScolastico());
        if (annoScolasticoOptional.isPresent()) {
            Festivita festivita = new Festivita();
            festivita.setInizio(festivitaDto.getInizio());
            festivita.setFine(festivitaDto.getFine());
            festivita.setDescrizione(festivitaDto.getDescrizione());
            festivita.setAnnoScolastico(annoScolasticoOptional.get());
            festivitaRepository.save(festivita);
            return festivita.getId();
        } else {
            throw new BadRequestException("L'anno scolastico indicato non esiste");
        }
    }

    public String deleteFestivita(Integer id) throws BadRequestException {
        Optional<Festivita> festivitaOptional = getFestivitaById(id);

        if (festivitaOptional.isPresent()) {
            festivitaRepository.delete(festivitaOptional.get());
            return "Festività con id "+id+" eliminata con successo";
        } else {
            throw new BadRequestException("La festività indicata non esiste");
        }
    }
}
