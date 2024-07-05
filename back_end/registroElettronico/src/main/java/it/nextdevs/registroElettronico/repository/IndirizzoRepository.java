package it.nextdevs.registroElettronico.repository;

import it.nextdevs.registroElettronico.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Integer> {
    Optional<Indirizzo> findByViaAndNumeroCivicoAndCittaAndProvincia(String via, String numeroCivico, String citta, String provincia);
}
