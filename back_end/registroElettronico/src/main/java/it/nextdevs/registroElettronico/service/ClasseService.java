package it.nextdevs.registroElettronico.service;

import it.nextdevs.registroElettronico.dto.ClasseDto;
import it.nextdevs.registroElettronico.dto.PatchAnnoStudenteDto;
import it.nextdevs.registroElettronico.model.*;
import it.nextdevs.registroElettronico.repository.ClasseRepository;
import it.nextdevs.registroElettronico.repository.IndirizzoScuolaRepository;
import it.nextdevs.registroElettronico.repository.StudenteRepository;
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
    @Autowired
    private StudenteRepository studenteRepository;

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

    public Classe updateClasse(ClasseDto classeDto, Integer id) throws BadRequestException {
        Optional<Classe> classeOptional = classeRepository.findById(id);
        if (classeOptional.isPresent()) {
            Optional<Istituto> istitutoOptional = istitutoService.getIstitutoById(classeDto.getCodiceIstituto());
            if (istitutoOptional.isPresent()) {
                Optional<IndirizzoScuola> indirizzoScuolaOptional = indirizzoScuolaRepository.findById(classeDto.getIndirizzo());

                if (indirizzoScuolaOptional.isPresent()) {
                    Optional<AnnoScolastico> annoScolasticoOptional = annoScolasticoService.getAnnoById(classeDto.getAnnoScolastico());
                    if (annoScolasticoOptional.isPresent()) {
                        Classe classe = classeOptional.get();
                        classe.setNome(classeDto.getNome());
                        classe.setIstituto(istitutoOptional.get());
                        classe.setIndirizzo(indirizzoScuolaOptional.get());
                        classe.setAnnoScolastico(annoScolasticoOptional.get());
                        return classeRepository.save(classe);
                    } else {
                        throw new BadRequestException("Anno Scolastico non trovato");
                    }
                } else {
                    throw new BadRequestException("Indirizzo Scolastico non trovato");
                }
            } else {
                throw new BadRequestException("Istituto non trovato");
            }
        } else {
            throw new BadRequestException("Classe non trovata");
        }
    }

    public Classe patchClasseStudente(Integer idClasse, PatchAnnoStudenteDto patchAnnoStudenteDto) throws BadRequestException {
        Optional<Classe> classeOptional = classeRepository.findById(idClasse);

        if (classeOptional.isPresent()) {
            Classe classe = classeOptional.get();
            List<Studente> studenti = classe.getStudenti();
            patchAnnoStudenteDto.getStudenti().forEach((studenteId) -> {
                Optional<Studente> studenteOptional = studenteRepository.findById(studenteId);
                studenteOptional.ifPresent(studenti::add);
            });
            classe.setStudenti(studenti);
            return classeRepository.save(classe);
        } else {
            throw new BadRequestException("Classe non trovata");
        }
    }
}
