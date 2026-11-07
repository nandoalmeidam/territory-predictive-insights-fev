package br.com.pluvia.sensor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

import br.com.pluvia.exception.FonteIndisponivelException;

// Testes automatizados do sensor de chuva.
public class SensorChuvaTest {

	// Verifica se um sensor desligado impede a leitura.
	@Test
	void deveLancarExcecaoQuandoSensorEstiverDesligado() {

		// Arrange: cria o sensor e o desliga.
		SensorChuva sensor = new SensorChuva();
		sensor.setFuncionando(false);

		// Act: tenta realizar uma leitura.
		FonteIndisponivelException excecao = assertThrows(FonteIndisponivelException.class, () -> sensor.lerValor());

		// Assert: verifica o estado e a mensagem de erro.
		assertFalse(sensor.estaFuncionando());

		assertEquals("O sensor de chuva está indisponível.", excecao.getMessage());
	}

	// Verifica se o sensor funcionando retorna uma leitura válida.
	@Test
	void deveRetornarValorValidoQuandoSensorEstiverFuncionando() {

		// Arrange: cria um sensor de chuva.
		SensorChuva sensor = new SensorChuva();

		// Act: realiza uma leitura simulada.
		double valor = sensor.lerValor();

		// Assert: verifica o estado e os limites da leitura.
		assertAll(() -> assertTrue(sensor.estaFuncionando()), () -> assertTrue(valor >= 0),
				() -> assertTrue(valor < 100));
	}
}
