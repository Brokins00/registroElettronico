package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.AnnoScolasticoDto;
import it.nextdevs.registroElettronico.model.AnnoScolastico;
import it.nextdevs.registroElettronico.model.Istituto;
import it.nextdevs.registroElettronico.repository.AnnoScolasticoRepository;
import it.nextdevs.registroElettronico.repository.IstitutoRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnoScolasticoService {
    @Autowired
    private AnnoScolasticoRepository annoScolasticoRepository;
    @Autowired
    private IstitutoRepository istitutoRepository;

    public List<AnnoScolastico> getAllAnni() {
        return annoScolasticoRepository.findAll();
    }

    public Optional<AnnoScolastico> getAnnoById(Integer id) {
        return annoScolasticoRepository.findById(id);
    }

    public Integer saveAnno(AnnoScolasticoDto annoScolasticoDto) throws BadRequestException {
        Optional<Istituto> istitutoOptional = istitutoRepository.findById(annoScolasticoDto.getCodiceIstituto());
        if (istitutoOptional.isPresent()) {
            Istituto istituto = istitutoOptional.get();
            AnnoScolastico annoScolastico = new AnnoScolastico();
            annoScolastico.setIstituto(istituto);
            annoScolastico.setInizio(annoScolasticoDto.getInizio());
            annoScolastico.setFine(annoScolasticoDto.getFine());
            annoScolasticoRepository.save(annoScolastico);
            return annoScolastico.getId();
        } else {
            throw new BadRequestException("Il codice dell'istituto non esiste");
        }
    }

    public String deleteAnno(Integer id) throws BadRequestException {
        Optional<AnnoScolastico> annoScolasticoOptional = getAnnoById(id);

        if (annoScolasticoOptional.isPresent()) {
            annoScolasticoRepository.delete(annoScolasticoOptional.get());
            return "Anno Scolastico con id "+id+" eliminato con successo";
        } else {
            throw new BadRequestException("L'anno scolastico indicato non esiste");
        }
    }
}
