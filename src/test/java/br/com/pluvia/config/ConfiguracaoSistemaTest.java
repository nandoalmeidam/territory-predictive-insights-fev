package br.com.pluvia.config;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

// Testa o padrão Singleton da configuração do sistema.
public class ConfiguracaoSistemaTest {
	@Test
	void deveRetornarSempreAMesmaInstancia() {
		// Arrange: obtém a primeira instância.
		ConfiguracaoSistema primeira = ConfiguracaoSistema.getInstancia();
		// Act: solicita novamente a instância.
		ConfiguracaoSistema segunda = ConfiguracaoSistema.getInstancia();
		// Assert: verifica se ambas são o mesmo objeto.
		assertSame(primeira, segunda);
	}
}
