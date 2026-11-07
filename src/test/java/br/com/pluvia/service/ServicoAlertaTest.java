package br.com.pluvia.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import br.com.pluvia.model.Alerta;
import br.com.pluvia.model.RegiaoMonitorada;
import br.com.pluvia.repository.AlertaRepository;

// Testa a geração de alertas utilizando Mockito.
public class ServicoAlertaTest {
	@Test
	void deveGerarESalvarAlertaCritico() {
		// Arrange: cria um repositório simulado.
		AlertaRepository repository = mock(AlertaRepository.class);
		// Simula o comportamento do método save().
		when(repository.save(any(Alerta.class))).thenAnswer(chamada -> chamada.getArgument(0));
		// Cria o serviço utilizando o repositório simulado.
		ServicoAlerta servico = new ServicoAlerta(repository);
		// Prepara uma região com chuva acima do limite crítico.
		RegiaoMonitorada regiao = new RegiaoMonitorada(1L, "Centro", "Sao Paulo");
		regiao.setVolumeChuva(90.0);
		// Act: gera o alerta.
		Alerta alerta = servico.gerarAlerta(regiao);
		// Assert: verifica o nível e a justificativa.
		assertEquals("CRITICO", alerta.getNivel());
		assertTrue(alerta.getMotivo().contains("Centro"));
		assertTrue(alerta.getMotivo().contains("90,00 mm"));
		// Confirma que o serviço solicitou o salvamento.
		verify(repository).save(any(Alerta.class));
	}
}
