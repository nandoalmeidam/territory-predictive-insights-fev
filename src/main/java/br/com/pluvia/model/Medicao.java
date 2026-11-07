package br.com.pluvia.model;

import java.time.LocalDateTime;

import br.com.pluvia.exception.DadosInvalidosException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Representa uma medição realizada por um sensor.
@Entity
public class Medicao {

	// Identificador único gerado pelo banco.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Tipo do sensor que realizou a medição.
	private String tipoSensor;

	// Valor registrado pelo sensor.
	private double valor;

	// Data e hora da medição.
	private LocalDateTime dataHora;

	// Construtor vazio exigido pelo JPA.
	public Medicao() {
	}

	// Construtor utilizado para criar medições.
	public Medicao(String tipoSensor, double valor) {
		setTipoSensor(tipoSensor);
		setValor(valor);
		this.dataHora = LocalDateTime.now();
	}

	// Métodos getters e setters.

	public Long getId() {
		return id;
	}

	public String getTipoSensor() {
		return tipoSensor;
	}

	public void setTipoSensor(String tipoSensor) {

		// Impede um tipo de sensor vazio ou nulo.
		if (tipoSensor == null || tipoSensor.isBlank()) {
			throw new DadosInvalidosException("O tipo do sensor não pode estar vazio.");
		}

		this.tipoSensor = tipoSensor;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {

		// Impede valores negativos, infinitos ou NaN.
		if (!Double.isFinite(valor) || valor < 0) {
			throw new DadosInvalidosException("O valor da medição deve ser um número válido e não negativo.");
		}

		this.valor = valor;
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
}
