package br.com.pluvia.builder;

import br.com.pluvia.model.Alerta;
import br.com.pluvia.exception.DadosInvalidosException;

// Builder responsável pela construção de alertas.
public class AlertaBuilder {

	private String nivel;
	private String motivo;
	private String regiao;
	private Double volumeChuva;

	// Define o nível do alerta.
	public AlertaBuilder comNivel(String nivel) {
		this.nivel = nivel;
		return this;
	}

	// Define o motivo do alerta.
	public AlertaBuilder comMotivo(String motivo) {
		this.motivo = motivo;
		return this;
	}

	// Define a região monitorada.
	public AlertaBuilder comRegiao(String regiao) {
		this.regiao = regiao;
		return this;
	}

	// Define o volume de chuva.
	public AlertaBuilder comVolumeChuva(double volumeChuva) {
		if (!Double.isFinite(volumeChuva) || volumeChuva < 0) {
			throw new DadosInvalidosException("O volume de chuva deve ser válido e não negativo.");
		}

		this.volumeChuva = volumeChuva;
		return this;
	}

	// Constrói e retorna o alerta.
	public Alerta construir() {

		if (motivo == null || motivo.isBlank()) {
			throw new DadosInvalidosException("O motivo do alerta não pode estar vazio.");
		}

		String motivoCompleto = motivo;

		if (regiao != null && !regiao.isBlank()) {
			motivoCompleto += " Regiao: " + regiao + ".";
		}

		if (volumeChuva != null) {
			motivoCompleto += String.format(java.util.Locale.forLanguageTag("pt-BR"), " Chuva: %.2f mm.", volumeChuva);
		}

		return new Alerta(nivel, motivoCompleto);
	}
}
