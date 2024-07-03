package it.nextdevs.registroElettronico.repository;

import it.nextdevs.registroElettronico.model.Valutazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValutazioneRepository extends JpaRepository<Valutazione, Integer> {
    List<Valutazione> findByStudente_IdAndAnnoScolastico_Id(Integer studenteId, Integer annoId);
}
