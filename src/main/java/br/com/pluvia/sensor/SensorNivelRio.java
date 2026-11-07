package br.com.pluvia.sensor;

import java.util.Random;

import br.com.pluvia.exception.FonteIndisponivelException;

// Sensor que simula o nível da água de um rio.
public class SensorNivelRio implements Sensor {

	// Gerador de valores aleatórios.
	private final Random random = new Random();

	// Indica se o sensor está funcionando.
	private boolean funcionando;

	// Construtor: inicia o sensor funcionando.
	public SensorNivelRio() {
		this.funcionando = true;
	}

	// Realiza uma leitura simulada do nível do rio.
	@Override
	public double lerValor() {

		if (!estaFuncionando()) {
			throw new FonteIndisponivelException("O sensor de nível do rio está indisponível.");
		}

		// Simula um nível entre 0 e 5 metros.
		return random.nextDouble() * 5;
	}

	// Verifica se o sensor está operacional.
	@Override
	public boolean estaFuncionando() {
		return funcionando;
	}

	// Identifica o tipo de sensor.
	@Override
	public String getTipo() {
		return "Nivel do Rio";
	}

	// Permite ativar ou desativar o sensor.
	public void setFuncionando(boolean funcionando) {
		this.funcionando = funcionando;
	}
}
