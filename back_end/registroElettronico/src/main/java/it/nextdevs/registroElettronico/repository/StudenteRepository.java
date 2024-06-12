package it.nextdevs.registroElettronico.repository;

import it.nextdevs.registroElettronico.model.Studente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudenteRepository extends JpaRepository<Studente, Integer> {
}
