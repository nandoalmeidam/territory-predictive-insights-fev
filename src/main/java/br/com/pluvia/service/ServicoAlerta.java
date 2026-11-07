package br.com.pluvia.service;

import org.springframework.stereotype.Service;
import br.com.pluvia.builder.AlertaBuilder;
import br.com.pluvia.model.Alerta;
import br.com.pluvia.model.RegiaoMonitorada;
import br.com.pluvia.repository.AlertaRepository;

// Serviço responsável por gerar e salvar alertas.
@Service
public class ServicoAlerta {
	private final AlertaRepository alertaRepository;

	// Recebe o repositório pelo construtor.
	public ServicoAlerta(AlertaRepository alertaRepository) {
		this.alertaRepository = alertaRepository;
	}

	// Avalia a região e salva o alerta no banco.
	public Alerta gerarAlerta(RegiaoMonitorada regiao) {
		// Atualiza o status da região monitorada.
		regiao.verificarStatus();
		String nivel = regiao.getStatus();
		String motivo;
		// Define a justificativa conforme o nível de risco.
		switch (nivel) {
		case "CRITICO":
			motivo = "Volume de chuva muito elevado. Risco critico na regiao.";
			break;
		case "ALERTA":
			motivo = "Volume de chuva elevado. Situacao de alerta.";
			break;
		case "ATENCAO":
			motivo = "Volume de chuva acima do limite de atencao.";
			break;
		default:
			motivo = "Volume de chuva dentro dos limites normais.";
			break;
		}
		// Utiliza o padrão Builder para construir o alerta.
		Alerta alerta = new AlertaBuilder().comNivel(nivel).comMotivo(motivo).comRegiao(regiao.getNome())
				.comVolumeChuva(regiao.getVolumeChuva()).construir();

		// Salva o alerta no H2 e retorna o registro.
		return alertaRepository.save(alerta);
	}
}
