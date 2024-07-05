package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.IndirizzoScuolaDto;
import it.nextdevs.registroElettronico.model.IndirizzoScuola;
import it.nextdevs.registroElettronico.model.Istituto;
import it.nextdevs.registroElettronico.repository.IndirizzoScuolaRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IndirizzoScuolaService {
    @Autowired
    private IndirizzoScuolaRepository indirizzoScuolaRepository;
    @Autowired
    private IstitutoService istitutoService;

    public List<IndirizzoScuola> getAllIndirizzi() {
        return indirizzoScuolaRepository.findAll();
    }

    public Integer saveIndirizzoScuola(IndirizzoScuolaDto indirizzoScuolaDto) throws BadRequestException {
        Optional<Istituto> istitutoOptional = istitutoService.getIstitutoById(indirizzoScuolaDto.getCodiceIstituto());

        if (istitutoOptional.isPresent()) {
            Istituto istituto = istitutoOptional.get();
            IndirizzoScuola indirizzoScuola = new IndirizzoScuola();
            indirizzoScuola.setNome(indirizzoScuolaDto.getNomeIndirizzo());
            indirizzoScuolaRepository.save(indirizzoScuola);
            List<IndirizzoScuola> indirizzi = istituto.getIndirizzi();
            indirizzi.add(indirizzoScuola);
            istituto.setIndirizzi(indirizzi);
            istitutoService.updateIstituto(istituto);
            return indirizzoScuola.getId();
        } else {
            throw new BadRequestException("L'istituto indicato non esiste");
        }
    }
}
