package br.com.pluvia.factory;

import br.com.pluvia.exception.DadosInvalidosException;
import br.com.pluvia.sensor.Sensor;
import br.com.pluvia.sensor.SensorChuva;
import br.com.pluvia.sensor.SensorNivelRio;
import br.com.pluvia.sensor.SensorUmidadeSolo;

// Factory responsável por criar os sensores do Pluvia.
public class SensorFactory {
	// Cria um sensor conforme o tipo solicitado.
	public static Sensor criarSensor(String tipo) {
		// Verifica se o tipo foi informado.
		if (tipo == null || tipo.isBlank()) {
			throw new DadosInvalidosException("O tipo do sensor não pode estar vazio.");
		}
		// Seleciona a implementação correspondente.
		switch (tipo.toUpperCase()) {
		case "CHUVA":
			return new SensorChuva();
		case "RIO":
			return new SensorNivelRio();
		case "SOLO":
			return new SensorUmidadeSolo();
		default:
			throw new DadosInvalidosException("Tipo de sensor inválido: " + tipo);
		}
	}
}
