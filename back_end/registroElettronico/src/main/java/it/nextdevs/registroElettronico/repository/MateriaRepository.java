package it.nextdevs.registroElettronico.repository;

import it.nextdevs.registroElettronico.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {
}
