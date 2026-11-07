package br.com.pluvia.sensor;

import java.util.Random;

import br.com.pluvia.exception.FonteIndisponivelException;

// Sensor que simula a umidade do solo em porcentagem.
public class SensorUmidadeSolo implements Sensor {

	// Gerador de valores aleatórios.
	private final Random random = new Random();

	// Indica se o sensor está funcionando.
	private boolean funcionando;

	// Construtor: inicia o sensor funcionando.
	public SensorUmidadeSolo() {
		this.funcionando = true;
	}

	// Realiza uma leitura simulada da umidade do solo.
	@Override
	public double lerValor() {

		if (!estaFuncionando()) {
			throw new FonteIndisponivelException("O sensor de umidade do solo está indisponível.");
		}

		// Simula uma umidade entre 0% e 100%.
		return random.nextDouble() * 100;
	}

	// Verifica se o sensor está operacional.
	@Override
	public boolean estaFuncionando() {
		return funcionando;
	}

	// Identifica o tipo de sensor.
	@Override
	public String getTipo() {
		return "Umidade do Solo";
	}

	// Permite ativar ou desativar o sensor.
	public void setFuncionando(boolean funcionando) {
		this.funcionando = funcionando;
	}
}
