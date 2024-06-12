package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.IstitutoDto;
import it.nextdevs.registroElettronico.model.Istituto;
import it.nextdevs.registroElettronico.repository.IstitutoRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IstitutoService {
    @Autowired
    private IstitutoRepository istitutoRepository;

    public Page<Istituto> getAllIstituti(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return istitutoRepository.findAll(pageable);
    }

    public Optional<Istituto> getIstitutoById(String id) {
        return istitutoRepository.findById(id);
    }

    public String saveIstituto(IstitutoDto istitutoDto) throws BadRequestException {
        if (getIstitutoById(istitutoDto.getCodiceUnivoco()).isEmpty()) {
            Istituto istituto = new Istituto();
            istituto.setNome(istitutoDto.getNome());
            istituto.setCodiceUnivoco(istitutoDto.getCodiceUnivoco());
            istitutoRepository.save(istituto);
            return istituto.getCodiceUnivoco();
        } else {
            throw new BadRequestException("Il codice dell'istituto " + istitutoDto.getCodiceUnivoco() + " già presente");
        }
    }

    public String deleteIstituto(String id) throws BadRequestException {
        Optional<Istituto> istitutoOptional = getIstitutoById(id);

        if (istitutoOptional.isPresent()) {
            istitutoRepository.delete(istitutoOptional.get());
            return "Istituto con codice "+id+" eliminato con successo";
        } else {
            throw new BadRequestException("Il codice dell'istituto " + id + " non è presente");
        }
    }
}
