package it.nextdevs.registroElettronico.repository;

import it.nextdevs.registroElettronico.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocenteRepository extends JpaRepository<Docente, Integer> {
}
