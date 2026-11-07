package br.com.pluvia.service;

import org.springframework.stereotype.Service;

import br.com.pluvia.exception.DadosInvalidosException;
import br.com.pluvia.factory.SensorFactory;
import br.com.pluvia.model.Alerta;
import br.com.pluvia.model.Medicao;
import br.com.pluvia.model.RegiaoMonitorada;
import br.com.pluvia.repository.MedicaoRepository;
import br.com.pluvia.sensor.Sensor;

// Serviço responsável por coordenar o monitoramento.
@Service
public class ServicoMonitoramento {

	private final MedicaoRepository medicaoRepository;
	private final ServicoAlerta servicoAlerta;

	// Recebe as dependências pelo construtor.
	public ServicoMonitoramento(MedicaoRepository medicaoRepository, ServicoAlerta servicoAlerta) {

		this.medicaoRepository = medicaoRepository;
		this.servicoAlerta = servicoAlerta;
	}

	// Realiza a leitura de um sensor e salva no banco.
	public Medicao realizarLeitura(String tipoSensor) {

		// Cria o sensor utilizando o padrão Factory.
		Sensor sensor = SensorFactory.criarSensor(tipoSensor);

		// Obtém o valor simulado pelo sensor.
		double valor = sensor.lerValor();

		// Cria uma medição válida.
		Medicao medicao = new Medicao(sensor.getTipo(), valor);

		// Salva a medição no H2.
		return medicaoRepository.save(medicao);
	}

	// Avalia uma medição de chuva e gera um alerta.
	public Alerta avaliarChuva(RegiaoMonitorada regiao, Medicao medicao) {

		// Verifica se os dados foram informados.
		if (regiao == null || medicao == null) {
			throw new DadosInvalidosException("A região e a medição são obrigatórias.");
		}

		// Apenas medições de chuva podem ser avaliadas aqui.
		if (!"Pluviometro".equals(medicao.getTipoSensor())) {
			throw new DadosInvalidosException("A avaliação de chuva exige uma medição do pluviômetro.");
		}

		// Atualiza o volume de chuva da região.
		regiao.setVolumeChuva(medicao.getValor());

		// Gera e salva o alerta no banco H2.
		return servicoAlerta.gerarAlerta(regiao);
	}
}
