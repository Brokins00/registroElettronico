package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.ClasseDto;
import it.nextdevs.registroElettronico.model.AnnoScolastico;
import it.nextdevs.registroElettronico.model.Classe;
import it.nextdevs.registroElettronico.model.IndirizzoScuola;
import it.nextdevs.registroElettronico.model.Istituto;
import it.nextdevs.registroElettronico.repository.ClasseRepository;
import it.nextdevs.registroElettronico.repository.IndirizzoScuolaRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private AnnoScolasticoService annoScolasticoService;
    @Autowired
    private IstitutoService istitutoService;
    @Autowired
    private IndirizzoScuolaRepository indirizzoScuolaRepository;

    public List<Classe> getAllClassi() {
        return classeRepository.findAll();
    }

    public Integer saveClasse(ClasseDto classeDto) throws BadRequestException {
        Optional<Istituto> istitutoOptional = istitutoService.getIstitutoById(classeDto.getCodiceIstituto());

        if (istitutoOptional.isPresent()) {
            Optional<AnnoScolastico> annoScolasticoOptional = annoScolasticoService.getAnnoById(classeDto.getAnnoScolastico());
            if (annoScolasticoOptional.isPresent()) {
                Optional<IndirizzoScuola> indirizzoScuolaOptional = indirizzoScuolaRepository.findById(classeDto.getIndirizzo());

                if (indirizzoScuolaOptional.isPresent()) {
                    Istituto istituto = istitutoOptional.get();
                    AnnoScolastico annoScolastico = annoScolasticoOptional.get();
                    IndirizzoScuola indirizzoScuola = indirizzoScuolaOptional.get();
                    Classe classe = new Classe();
                    classe.setNome(classeDto.getNome());
                    classe.setIstituto(istituto);
                    classe.setIndirizzo(indirizzoScuola);
                    classe.setAnnoScolastico(annoScolastico);
                    classeRepository.save(classe);
                    return classe.getId();
                } else {
                    throw new BadRequestException("L'indirizzo scolastico non esiste");
                }
            } else {
                throw new BadRequestException("L'anno scolastico non esiste");
            }
        } else {
            throw new BadRequestException("Il codice dell'istituto non esiste");
        }
    }

}
