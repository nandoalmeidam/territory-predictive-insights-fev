package br.com.pluvia.previsao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

// Testa o algoritmo de previsão por comparação.
public class PrevisaoComparacaoTest {
	@Test
	void deveCalcularPrevisaoPorComparacao() {
		// Arrange: prepara o histórico e a estratégia.
		List<Double> medicoes = List.of(20.0, 30.0, 40.0, 50.0);
		PrevisaoComparacao previsao = new PrevisaoComparacao();
		// Act: executa o algoritmo.
		double resultado = previsao.prever(medicoes);
		// Assert: verifica a previsão calculada.
		assertEquals(65.0, resultado, 0.001);
	}
}
