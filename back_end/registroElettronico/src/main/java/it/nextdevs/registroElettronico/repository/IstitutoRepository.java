package it.nextdevs.registroElettronico.repository;

import it.nextdevs.registroElettronico.model.Istituto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IstitutoRepository extends JpaRepository<Istituto, String> {
}
