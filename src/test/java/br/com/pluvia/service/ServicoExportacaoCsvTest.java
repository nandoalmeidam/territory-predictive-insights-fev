package br.com.pluvia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.pluvia.model.Alerta;
import br.com.pluvia.repository.AlertaRepository;
import br.com.pluvia.repository.MedicaoRepository;

// Testes automatizados da exportação CSV.
class ServicoExportacaoCsvTest {

	private MedicaoRepository medicaoRepository;
	private AlertaRepository alertaRepository;
	private ServicoExportacaoCsv servico;

	// O JUnit cria uma pasta temporária para cada teste.
	@TempDir
	Path pastaTemporaria;

	@BeforeEach
	void prepararTeste() {

		// Cria repositórios simulados, sem acessar o H2.
		medicaoRepository = mock(MedicaoRepository.class);
		alertaRepository = mock(AlertaRepository.class);

		// Cria o serviço com os repositórios simulados.
		servico = new ServicoExportacaoCsv(medicaoRepository, alertaRepository);
	}

	@Test
    void deveExportarMedicoesParaCsv() throws IOException {

        // Simula uma consulta sem medições.
        when(medicaoRepository.findAll())
                .thenReturn(List.of());

        // Define um arquivo dentro da pasta temporária.
        Path destino = pastaTemporaria.resolve("medicoes.csv");

        // Executa a exportação.
        Path arquivo = servico.exportarMedicoes(destino);

        // Verifica se o arquivo foi criado.
        assertTrue(Files.exists(arquivo));

        // Lê o conteúdo do CSV.
        String conteudo = Files.readString(
                arquivo,
                StandardCharsets.UTF_8);

        // Confere o cabeçalho.
        assertTrue(conteudo.contains(
                "ID;Sensor;Valor;Data e Hora"));

        // Confirma que o repositório foi consultado.
        verify(medicaoRepository).findAll();
    }

	@Test
    void deveExportarAlertasParaCsv() throws IOException {

        // Simula uma consulta sem alertas.
        when(alertaRepository.findAll())
                .thenReturn(List.of());

        Path destino = pastaTemporaria.resolve("alertas.csv");

        Path arquivo = servico.exportarAlertas(destino);

        assertTrue(Files.exists(arquivo));

        String conteudo = Files.readString(
                arquivo,
                StandardCharsets.UTF_8);

        assertTrue(conteudo.contains(
                "ID;Nivel;Motivo;Data e Hora"));

        verify(alertaRepository).findAll();
    }

	@Test
	void deveExportarMotivoComPontoEVirgula() throws IOException {

		// Cria um alerta com ponto e vírgula no motivo.
		Alerta alerta = new Alerta("CRITICO", "Chuva intensa; risco de alagamento");

		when(alertaRepository.findAll()).thenReturn(List.of(alerta));

		Path destino = pastaTemporaria.resolve("alertas.csv");

		Path arquivo = servico.exportarAlertas(destino);

		String conteudo = Files.readString(arquivo, StandardCharsets.UTF_8);

		// O motivo deve permanecer em uma única coluna.
		assertTrue(conteudo.contains("\"Chuva intensa; risco de alagamento\""));
	}
}
