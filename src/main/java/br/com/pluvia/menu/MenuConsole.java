package br.com.pluvia.menu;

import java.io.IOException;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.stereotype.Component;

import br.com.pluvia.exception.DadosInvalidosException;
import br.com.pluvia.exception.FonteIndisponivelException;

import br.com.pluvia.exception.DadosInvalidosException;
import br.com.pluvia.exception.FonteIndisponivelException;
import br.com.pluvia.model.Alerta;
import br.com.pluvia.model.Medicao;
import br.com.pluvia.model.RegiaoMonitorada;
import br.com.pluvia.previsao.EstrategiaPrevisao;
import br.com.pluvia.previsao.PrevisaoComparacao;
import br.com.pluvia.previsao.PrevisaoMediaMovel;
import br.com.pluvia.repository.AlertaRepository;
import br.com.pluvia.repository.MedicaoRepository;
import br.com.pluvia.service.ServicoMonitoramento;
import br.com.pluvia.service.ServicoPrevisao;
import br.com.pluvia.service.ServicoExportacaoCsv;
import br.com.pluvia.service.ServicoMonitoramento;
import br.com.pluvia.service.ServicoPrevisao;

// Menu principal do sistema Pluvia.
@Component
public class MenuConsole {

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	private final Scanner scanner;
	private final ServicoMonitoramento monitoramento;
	private final ServicoExportacaoCsv exportacaoCsv;
	private final ServicoPrevisao previsao;
	private final MedicaoRepository medicaoRepository;
	private final AlertaRepository alertaRepository;
	private final RegiaoMonitorada regiaoMonitorada;

	// Recebe os serviços e repositórios necessários.
	public MenuConsole(ServicoMonitoramento monitoramento, ServicoExportacaoCsv exportacaoCsv,
			MedicaoRepository medicaoRepository, AlertaRepository alertaRepository) {

		this.scanner = new Scanner(System.in);
		this.monitoramento = monitoramento;
		this.exportacaoCsv = exportacaoCsv;
		this.previsao = new ServicoPrevisao();
		this.medicaoRepository = medicaoRepository;
		this.alertaRepository = alertaRepository;
		this.regiaoMonitorada = new RegiaoMonitorada(1L, "Centro", "Sao Paulo");
	}

	// Inicia o menu interativo.
	public void iniciar() {

		int opcao = -1;

		while (opcao != 0) {

			exibirMenu();

			try {
				System.out.print("Escolha uma opcao: ");
				opcao = Integer.parseInt(scanner.nextLine());

				switch (opcao) {
				case 1:
					realizarLeituraSensor();
					break;
				case 2:
					consultarPrevisaoChuva();
					break;
				case 3:
					exibirHistoricoMedicoes();
					break;
				case 4:
					exibirHistoricoAlertas();
					break;
				case 5:
					exibirDadosTerritoriais();
					break;
				case 6:
					exibirStatusSistema();
					break;
				case 7:
					exportarMedicoesCsv();
					break;
				case 0:
					System.out.println("Encerrando o Pluvia. Ate logo!");
					break;
				default:
					System.out.println("Opcao invalida. Tente novamente.");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Digite apenas numeros do menu.");
			}
		}
	}

	// Exibe as opções disponíveis.
	private void exibirMenu() {

		System.out.println();
		System.out.println("======================================");
		System.out.println("       PLUVIA - MONITORAMENTO");
		System.out.println("======================================");
		System.out.println("1 - Realizar leitura dos sensores");
		System.out.println("2 - Consultar previsao de chuva");
		System.out.println("3 - Historico de medicoes");
		System.out.println("4 - Historico de alertas");
		System.out.println("5 - Dados territoriais");
		System.out.println("6 - Status do sistema");
		System.out.println("7 - Exportar dados para CSV");
		System.out.println("0 - Sair");
		System.out.println("======================================");
	}

	// Permite escolher um sensor e registrar sua leitura.
	private void realizarLeituraSensor() {

		System.out.println();
		System.out.println("===== LEITURA DOS SENSORES =====");
		System.out.println("1 - Pluviometro");
		System.out.println("2 - Nivel do Rio");
		System.out.println("3 - Umidade do Solo");
		System.out.println("0 - Voltar");
		System.out.print("Escolha o sensor: ");

		String opcaoSensor = scanner.nextLine();
		String tipoSensor;

		// Identifica qual sensor o usuario escolheu.
		switch (opcaoSensor) {
		case "1":
			tipoSensor = "CHUVA";
			break;
		case "2":
			tipoSensor = "RIO";
			break;
		case "3":
			tipoSensor = "SOLO";
			break;
		case "0":
			return;
		default:
			System.out.println("Sensor invalido.");
			return;
		}

		try {
			// Realiza a leitura e salva a medicao no H2.
			Medicao medicao = monitoramento.realizarLeitura(tipoSensor);

			System.out.println();
			System.out.println("Medicao registrada com sucesso!");
			System.out.println("Sensor: " + medicao.getTipoSensor());
			System.out.printf("Valor: %.2f%n", medicao.getValor());
			System.out.println("Data: " + medicao.getDataHora().format(FORMATO_DATA));

			// Apenas o pluviometro gera alertas de chuva.
			if ("CHUVA".equals(tipoSensor)) {

				Alerta alerta = monitoramento.avaliarChuva(regiaoMonitorada, medicao);

				System.out.println();
				System.out.println("===== RESULTADO DO MONITORAMENTO =====");
				System.out.println("Regiao: " + regiaoMonitorada.getNome());
				System.out.println("Nivel: " + alerta.getNivel());
				System.out.println("Motivo: " + alerta.getMotivo());
			}
		} catch (DadosInvalidosException | FonteIndisponivelException e) {
			System.out.println("Erro no monitoramento: " + e.getMessage());
		}
	}

	// Consulta e apresenta as medicoes armazenadas no banco H2.
	private void exibirHistoricoMedicoes() {

		System.out.println();
		System.out.println("===== HISTORICO DE MEDICOES =====");

		// Busca todas as medicoes registradas no banco.
		java.util.List<Medicao> medicoes = medicaoRepository.findAll();

		// Verifica se existem registros.
		if (medicoes.isEmpty()) {
			System.out.println("Nenhuma medicao registrada.");
			return;
		}

		// Exibe cada medicao encontrada.
		for (Medicao medicao : medicoes) {

			System.out.println("--------------------------------------");
			System.out.println("ID: " + medicao.getId());
			System.out.println("Sensor: " + medicao.getTipoSensor());
			System.out.printf("Valor: %.2f%n", medicao.getValor());
			System.out.println("Data: " + medicao.getDataHora().format(FORMATO_DATA));
		}

		System.out.println("--------------------------------------");
		System.out.println("Total de medicoes: " + medicoes.size());
	}

	// Consulta e apresenta os alertas armazenados no banco H2.
	private void exibirHistoricoAlertas() {

		System.out.println();
		System.out.println("===== HISTORICO DE ALERTAS =====");

		// Busca todos os alertas registrados no banco.
		java.util.List<Alerta> alertas = alertaRepository.findAll();

		// Verifica se existem alertas cadastrados.
		if (alertas.isEmpty()) {
			System.out.println("Nenhum alerta registrado.");
			return;
		}

		// Percorre e apresenta os alertas encontrados.
		for (Alerta alerta : alertas) {

			System.out.println("--------------------------------------");
			System.out.println("ID: " + alerta.getId());
			System.out.println("Nivel: " + alerta.getNivel());
			System.out.println("Motivo: " + alerta.getMotivo());
			System.out.println("Data: " + alerta.getDataHora().format(FORMATO_DATA));
		}

		System.out.println("--------------------------------------");
		System.out.println("Total de alertas: " + alertas.size());
	}

	// Apresenta os dados da regiao monitorada.
	private void exibirDadosTerritoriais() {

		System.out.println();
		System.out.println("===== DADOS TERRITORIAIS =====");

		// Atualiza o status conforme os limites configurados.
		regiaoMonitorada.verificarStatus();

		System.out.println("ID: " + regiaoMonitorada.getId());
		System.out.println("Nome: " + regiaoMonitorada.getNome());
		System.out.println("Regiao: " + regiaoMonitorada.getRegiao());

		System.out.printf("Volume de chuva: %.2f mm%n", regiaoMonitorada.getVolumeChuva());

		System.out.println("Status: " + regiaoMonitorada.getStatus());

		System.out.println();
		System.out.println("===== LIMITES DE CHUVA =====");

		System.out.printf("Atencao: %.2f mm%n", regiaoMonitorada.getDadosTerritorio().getLimiteAtencao());

		System.out.printf("Alerta: %.2f mm%n", regiaoMonitorada.getDadosTerritorio().getLimiteAlerta());

		System.out.printf("Critico: %.2f mm%n", regiaoMonitorada.getDadosTerritorio().getLimiteCritico());

		System.out.println();

		while (true) {

			System.out.print("Deseja alterar os limites? (S/N): ");

			String resposta = scanner.nextLine().trim();

			if (resposta.equalsIgnoreCase("S") || resposta.equalsIgnoreCase("SIM")) {

				configurarLimitesTerritoriais();
				return;
			}

			if (resposta.equalsIgnoreCase("N") || resposta.equalsIgnoreCase("NAO")
					|| resposta.equalsIgnoreCase("NÃO")) {

				System.out.println("Limites mantidos.");
				return;
			}

			System.out.println("Opcao invalida. Digite S para sim ou N para nao.");
		}
	}

	// Permite configurar os limites de chuva da regiao.
	private void configurarLimitesTerritoriais() {

		while (true) {

			System.out.println();
			System.out.println("===== CONFIGURAR LIMITES =====");
			System.out.println("Informe os valores na ordem:");
			System.out.println("ATENCAO, ALERTA, CRITICO");
			System.out.println();
			System.out.println("Exemplos: 30,60,90 ou 30;60;90");
			System.out.println("Digite 0 para cancelar.");
			System.out.print("Limites: ");

			String entrada = scanner.nextLine().trim();

			if (entrada.equals("0")) {
				System.out.println("Configuracao cancelada.");
				return;
			}

			try {
				// Aceita virgulas, ponto e virgula e a palavra "e".
				String texto = entrada.replaceAll("(?i)\\s+e\\s+", ",").replace(";", ",");

				String[] valores = texto.split(",", -1);

				if (valores.length != 3) {
					System.out.println("Erro: informe exatamente tres valores.");
					continue;
				}

				double atencao = Double.parseDouble(valores[0].trim());

				double alerta = Double.parseDouble(valores[1].trim());

				double critico = Double.parseDouble(valores[2].trim());

				// A classe DadosTerritorio valida os limites.
				regiaoMonitorada.configurarLimites(atencao, alerta, critico);

				System.out.println();
				System.out.println("Limites atualizados com sucesso!");

				System.out.printf("Atencao: %.2f mm%n", atencao);
				System.out.printf("Alerta: %.2f mm%n", alerta);
				System.out.printf("Critico: %.2f mm%n", critico);

				System.out.println("Status atual: " + regiaoMonitorada.getStatus());

				return;

			} catch (NumberFormatException e) {
				System.out.println("Erro: use apenas numeros. Exemplo: 30,60,90.");

			} catch (DadosInvalidosException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
	}

	// Exibe as informacoes gerais do sistema.
	private void exibirStatusSistema() {

		System.out.println();
		System.out.println("===== STATUS DO SISTEMA =====");

		// Consulta a configuracao Singleton.
		br.com.pluvia.config.ConfiguracaoSistema configuracao = br.com.pluvia.config.ConfiguracaoSistema.getInstancia();

		System.out.println("Sistema: " + configuracao.getNomeSistema());
		System.out.println("Situacao: EM EXECUCAO");

		System.out.println("Intervalo de leitura: " + configuracao.getIntervaloLeitura() + " segundos");

		System.out.println();
		System.out.println("===== REGIAO MONITORADA =====");

		regiaoMonitorada.verificarStatus();

		System.out.println("Nome: " + regiaoMonitorada.getNome());
		System.out.println("Regiao: " + regiaoMonitorada.getRegiao());
		System.out.println("Status: " + regiaoMonitorada.getStatus());

		System.out.println();
		System.out.println("===== BANCO DE DADOS =====");

		System.out.println("Total de medicoes: " + medicaoRepository.count());

		System.out.println("Total de alertas: " + alertaRepository.count());
	}

	// Consulta a previsao com base nas medicoes de chuva salvas.
	private void consultarPrevisaoChuva() {

		System.out.println();
		System.out.println("===== PREVISAO DE CHUVA =====");

		// Busca apenas medicoes do pluviometro.
		java.util.List<Medicao> medicoes = medicaoRepository.findAll().stream()
				.filter(m -> "Pluviometro".equals(m.getTipoSensor()))
				.sorted(java.util.Comparator.comparing(Medicao::getDataHora)).toList();

		if (medicoes.isEmpty()) {
			System.out.println("Nao existem medicoes de chuva.");
			System.out.println("Realize leituras do pluviometro primeiro.");
			return;
		}

		// Extrai os valores das medicoes.
		java.util.List<Double> valores = medicoes.stream().map(Medicao::getValor).toList();

		System.out.println("Medicoes de chuva disponiveis: " + valores.size());

		System.out.println();
		System.out.println("1 - Previsao por Media Movel");
		System.out.println("2 - Previsao por Comparacao");
		System.out.println("0 - Voltar");
		System.out.print("Escolha a estrategia: ");

		String opcao = scanner.nextLine().trim();

		EstrategiaPrevisao estrategia;

		switch (opcao) {
		case "1":
			estrategia = new PrevisaoMediaMovel(3);
			break;
		case "2":
			if (valores.size() < 2) {
				System.out.println("A comparacao exige pelo menos duas medicoes.");
				return;
			}

			estrategia = new PrevisaoComparacao();
			break;
		case "0":
			return;
		default:
			System.out.println("Opcao invalida.");
			return;
		}

		try {
			// Polimorfismo: executa a estrategia selecionada.
			double resultado = previsao.executarPrevisao(estrategia, valores);

			System.out.println();
			System.out.println("===== RESULTADO DA PREVISAO =====");
			System.out.println("Estrategia: " + estrategia.getNome());

			System.out.printf("Volume estimado: %.2f mm%n", resultado);

			System.out.println("Resultado baseado no historico de medicoes.");

		} catch (DadosInvalidosException e) {
			System.out.println("Erro na previsao: " + e.getMessage());
		}
	}

	// Exibe o submenu de exportação CSV.
	private void exportarMedicoesCsv() {

		System.out.println();
		System.out.println("===== EXPORTACAO CSV =====");
		System.out.println("1 - Exportar medicoes");
		System.out.println("2 - Exportar alertas");
		System.out.println("3 - Exportar ambos");
		System.out.println("0 - Voltar");
		System.out.print("Escolha uma opcao: ");

		String opcao = scanner.nextLine().trim();

		try {
			switch (opcao) {

			case "1": {
				// Abre a janela para escolher o destino.
				Path destino = escolherArquivoCsv("medicoes.csv");

				if (destino == null) {
					System.out.println("Exportacao cancelada.");
					break;
				}

				Path arquivo = exportacaoCsv.exportarMedicoes(destino);

				System.out.println("Medicoes exportadas com sucesso!");
				System.out.println("Arquivo: " + arquivo);
				break;
			}

			case "2": {
				// Abre a janela para escolher o destino.
				Path destino = escolherArquivoCsv("alertas.csv");

				if (destino == null) {
					System.out.println("Exportacao cancelada.");
					break;
				}

				Path arquivo = exportacaoCsv.exportarAlertas(destino);

				System.out.println("Alertas exportados com sucesso!");
				System.out.println("Arquivo: " + arquivo);
				break;
			}

			case "3": {
				// Escolhe o destino das medições.
				Path destinoMedicoes = escolherArquivoCsv("medicoes.csv");

				if (destinoMedicoes == null) {
					System.out.println("Exportacao cancelada.");
					break;
				}

				// Escolhe o destino dos alertas.
				Path destinoAlertas = escolherArquivoCsv("alertas.csv");

				if (destinoAlertas == null) {
					System.out.println("Exportacao cancelada.");
					break;
				}

				// Impede que os dois arquivos tenham o mesmo caminho.
				if (destinoMedicoes.toAbsolutePath().normalize().equals(destinoAlertas.toAbsolutePath().normalize())) {

					System.out.println("Escolha nomes diferentes para os arquivos.");
					break;
				}

				// Exporta somente após confirmar os dois destinos.
				Path arquivoMedicoes = exportacaoCsv.exportarMedicoes(destinoMedicoes);

				Path arquivoAlertas = exportacaoCsv.exportarAlertas(destinoAlertas);

				System.out.println("Exportacao concluida!");
				System.out.println("Medicoes: " + arquivoMedicoes);
				System.out.println("Alertas: " + arquivoAlertas);
				break;
			}

			case "0":
				System.out.println("Voltando ao menu principal...");
				break;

			default:
				System.out.println("Opcao invalida!");
				break;
			}

		} catch (IOException e) {
			System.out.println("Erro durante a exportacao: " + e.getMessage());
		}
	}

	// Abre a janela do Windows para escolher onde salvar o CSV.
	private Path escolherArquivoCsv(String nomeSugerido) {

		// Cria a janela de seleção de arquivos.
		JFileChooser seletor = new JFileChooser();

		// Define o título e o nome inicial do arquivo.
		seletor.setDialogTitle("Pluvia - Salvar arquivo CSV");
		seletor.setSelectedFile(new java.io.File(nomeSugerido));

		// Permite selecionar somente arquivos CSV.
		seletor.setFileFilter(new FileNameExtensionFilter("Arquivos CSV (*.csv)", "csv"));

		seletor.setAcceptAllFileFilterUsed(false);

		// Cria uma janela auxiliar para manter o Salvar como em primeiro plano.
		javax.swing.JFrame janelaAuxiliar = new javax.swing.JFrame();

		janelaAuxiliar.setAlwaysOnTop(true);
		janelaAuxiliar.setLocationRelativeTo(null);

		int resultado;

		try {
			// Abre o seletor vinculado à janela auxiliar.
			resultado = seletor.showSaveDialog(janelaAuxiliar);

		} finally {
			// Libera a janela auxiliar após fechar o seletor.
			janelaAuxiliar.dispose();
		}

		// Se o usuário cancelar, não gera arquivo.
		if (resultado != JFileChooser.APPROVE_OPTION) {
			return null;
		}

		// Recupera o caminho escolhido pelo usuário.
		Path arquivo = seletor.getSelectedFile().toPath();

		// Acrescenta .csv se o usuário não informar a extensão.
		if (!arquivo.getFileName().toString().toLowerCase(java.util.Locale.ROOT).endsWith(".csv")) {

			arquivo = Path.of(arquivo.toString() + ".csv");
		}

		// Confirma antes de substituir um arquivo existente.
		if (java.nio.file.Files.exists(arquivo)) {

			int confirmacao = JOptionPane.showConfirmDialog(null, "O arquivo ja existe. Deseja substitui-lo?",
					"Pluvia - Confirmar substituicao", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if (confirmacao != JOptionPane.YES_OPTION) {
				return null;
			}
		}

		return arquivo;
	}

}