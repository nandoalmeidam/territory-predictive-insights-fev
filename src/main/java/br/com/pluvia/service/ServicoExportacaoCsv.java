package br.com.pluvia.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import br.com.pluvia.model.Alerta;
import br.com.pluvia.model.Medicao;
import br.com.pluvia.repository.AlertaRepository;
import br.com.pluvia.repository.MedicaoRepository;

// Serviço responsável por exportar medições para CSV.
@Service
public class ServicoExportacaoCsv {

	private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	private final MedicaoRepository medicaoRepository;
	private final AlertaRepository alertaRepository;

	// Recebe os dois repositórios pelo construtor.
	public ServicoExportacaoCsv(MedicaoRepository medicaoRepository, AlertaRepository alertaRepository) {

		this.medicaoRepository = medicaoRepository;
		this.alertaRepository = alertaRepository;
	}

	// Busca as medições no banco e gera o arquivo CSV.
	public Path exportarMedicoes(Path arquivo) throws IOException {

		// Cria a pasta de destino, caso ainda não exista.
		Path pasta = arquivo.toAbsolutePath().getParent();
		Files.createDirectories(pasta);

		// Consulta os registros persistidos no H2.
		List<Medicao> medicoes = medicaoRepository.findAll();

		// Monta o conteúdo do arquivo.
		StringBuilder csv = new StringBuilder();
		csv.append("ID;Sensor;Valor;Data e Hora\n");

		for (Medicao medicao : medicoes) {

			String valor = String.format(Locale.forLanguageTag("pt-BR"), "%.2f", medicao.getValor());

			csv.append(medicao.getId()).append(";");
			csv.append(medicao.getTipoSensor()).append(";");
			csv.append(valor).append(";");
			csv.append(medicao.getDataHora().format(FORMATO_DATA));
			csv.append("\n");
		}

		// Salva o arquivo em UTF-8 com BOM para facilitar
		// a abertura de caracteres acentuados no Excel.
		Files.writeString(arquivo, "\uFEFF" + csv, StandardCharsets.UTF_8);

		return arquivo.toAbsolutePath();
	}

	// Busca os alertas no banco e gera o arquivo CSV.
	public Path exportarAlertas(Path arquivo) throws IOException {

		// Cria a pasta de destino, caso ainda não exista.
		Path pasta = arquivo.toAbsolutePath().getParent();
		Files.createDirectories(pasta);

		// Consulta os alertas persistidos no H2.
		List<Alerta> alertas = alertaRepository.findAll();

		// Monta o conteúdo do CSV.
		StringBuilder csv = new StringBuilder();
		csv.append("ID;Nivel;Motivo;Data e Hora\n");

		for (Alerta alerta : alertas) {

			csv.append(alerta.getId()).append(";");
			csv.append(alerta.getNivel()).append(";");
			csv.append(formatarCampoCsv(alerta.getMotivo())).append(";");
			csv.append(alerta.getDataHora().format(FORMATO_DATA));
			csv.append("\n");
		}

		// Salva em UTF-8 com BOM para compatibilidade com Excel.
		Files.writeString(arquivo, "\uFEFF" + csv, StandardCharsets.UTF_8);

		return arquivo.toAbsolutePath();
	}

	// Protege campos que contêm separadores, aspas ou quebras de linha.
	private String formatarCampoCsv(String texto) {

		if (texto == null) {
			return "";
		}

		String resultado = texto.replace("\"", "\"\"");

		if (resultado.contains(";") || resultado.contains("\"") || resultado.contains("\n")
				|| resultado.contains("\r")) {

			return "\"" + resultado + "\"";
		}

		return resultado;
	}

}
