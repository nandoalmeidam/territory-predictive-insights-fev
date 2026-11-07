package br.com.pluvia.sensor;

import java.util.Random;

import br.com.pluvia.exception.FonteIndisponivelException;

// Sensor que simula a medição de chuva em milímetros.
public class SensorChuva implements Sensor {

	// Gerador de valores aleatórios para simular chuva.
	private final Random random = new Random();

	// Indica se o sensor está funcionando.
	private boolean funcionando;

	// Construtor: inicia o sensor funcionando.
	public SensorChuva() {
		this.funcionando = true;
	}

	// Realiza uma leitura simulada entre 0 e 100 mm.
	@Override
	public double lerValor() {

		if (!estaFuncionando()) {
			throw new FonteIndisponivelException("O sensor de chuva está indisponível.");
		}

		// Gera um valor de 0 a 100 mm.
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
		return "Pluviometro";
	}

	// Permite ativar ou desativar o sensor.
	public void setFuncionando(boolean funcionando) {
		this.funcionando = funcionando;
	}
}
