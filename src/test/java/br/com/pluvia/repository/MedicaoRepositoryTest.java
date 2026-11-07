package br.com.pluvia.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.pluvia.model.Medicao;

// Teste unitário com Mockito, sem acessar o banco H2.
public class MedicaoRepositoryTest {

	@Test
	void deveConsultarMedicoesUsandoMock() {

		// Arrange: cria um repositório simulado.
		MedicaoRepository repository = mock(MedicaoRepository.class);

		// Prepara uma medição de exemplo.
		Medicao medicao = new Medicao("Pluviometro", 35.5);

		// Configura a resposta do repositório simulado.
		when(repository.findAll()).thenReturn(List.of(medicao));

		// Act: consulta as medições.
		List<Medicao> resultado = repository.findAll();

		// Assert: verifica os dados retornados.
		assertEquals(1, resultado.size());

		assertEquals(35.5, resultado.get(0).getValor());

		// Confirma que o método foi chamado.
		verify(repository).findAll();
	}
}
