package br.com.pluvia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pluvia.model.Medicao;

// Interface responsável pelo acesso aos dados das medições.
public interface MedicaoRepository extends JpaRepository<Medicao, Long> {

}
