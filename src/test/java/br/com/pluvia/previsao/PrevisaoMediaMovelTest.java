package br.com.pluvia.previsao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

// Testa o cálculo da previsão por média móvel.
public class PrevisaoMediaMovelTest {
	@Test
	void deveCalcularMediaDasUltimasMedicoes() {
		// Arrange: prepara as medições e a estratégia.
		List<Double> medicoes = List.of(10.0, 20.0, 30.0, 40.0, 50.0);
		PrevisaoMediaMovel previsao = new PrevisaoMediaMovel(3);
		// Act: executa o cálculo.
		double resultado = previsao.prever(medicoes);
		// Assert: verifica se a média está correta.
		assertEquals(40.0, resultado, 0.001);
	}
}
