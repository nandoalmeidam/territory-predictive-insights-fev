package br.com.pluvia.service;

import java.util.List;
import br.com.pluvia.previsao.EstrategiaPrevisao;

// Serviço responsável por executar as estratégias de previsão.
public class ServicoPrevisao {
	// Recebe qualquer estratégia que herde de EstrategiaPrevisao.
	public double executarPrevisao(EstrategiaPrevisao estrategia, List<Double> medicoes) {
		// Executa o método da estratégia recebida.
		return estrategia.prever(medicoes);
	}
}
