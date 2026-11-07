package br.com.pluvia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.pluvia.model.Alerta;

// Interface responsável pela persistência dos alertas no H2.
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
}
