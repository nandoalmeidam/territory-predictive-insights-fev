package br.com.pluvia.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.pluvia.exception.DadosInvalidosException;

// Testes automatizados da classe Medicao.
public class MedicaoTest {

	// Testa se uma medição negativa é rejeitada.
	@Test
	void deveRejeitarMedicaoNegativa() {

		// Arrange: prepara os dados do teste.
		String tipoSensor = "Pluviometro";
		double valorInvalido = -10.0;

		// Act: tenta criar uma medição inválida.
		DadosInvalidosException excecao = assertThrows(DadosInvalidosException.class,
				() -> new Medicao(tipoSensor, valorInvalido));

		// Assert: verifica se a mensagem está correta.
		assertEquals("O valor da medição deve ser um número válido e não negativo.", excecao.getMessage());
	}

	// Testa se uma medição válida é aceita.
	@Test
	void deveAceitarMedicaoValida() {

		// Arrange: prepara os dados.
		String tipoSensor = "Pluviometro";
		double valorValido = 35.5;

		// Act: cria a medição.
		Medicao medicao = new Medicao(tipoSensor, valorValido);

		// Assert: verifica os valores armazenados.
		assertEquals(tipoSensor, medicao.getTipoSensor());
		assertEquals(valorValido, medicao.getValor());
	}
}
