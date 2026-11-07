package br.com.pluvia.model;

import java.time.LocalDateTime;
import br.com.pluvia.exception.DadosInvalidosException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Entidade que representa um alerta de risco.
@Entity
public class Alerta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nivel;
	private String motivo;
	private LocalDateTime dataHora;

	// Construtor vazio exigido pelo JPA.
	public Alerta() {
	}

	// Construtor utilizado para criar novos alertas.
	public Alerta(String nivel, String motivo) {
		setNivel(nivel);
		setMotivo(motivo);
		this.dataHora = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		if (nivel == null || (!nivel.equals("NORMAL") && !nivel.equals("ATENCAO") && !nivel.equals("ALERTA")
				&& !nivel.equals("CRITICO"))) {
			throw new DadosInvalidosException("O nível do alerta deve ser NORMAL, ATENCAO, ALERTA ou CRITICO.");
		}
		this.nivel = nivel;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		if (motivo == null || motivo.isBlank()) {
			throw new DadosInvalidosException("O motivo do alerta não pode estar vazio.");
		}
		this.motivo = motivo;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}
}
