package br.com.pluvia.previsao;

import java.util.List;

import br.com.pluvia.exception.DadosInvalidosException;

// Classe abstrata que define a estrutura das previsões.
public abstract class EstrategiaPrevisao {

	// Nome da estratégia utilizada.
	private String nome;

	// Construtor da classe abstrata.
	public EstrategiaPrevisao(String nome) {
		this.nome = nome;
	}

	// Cada classe filha implementará seu próprio cálculo.
	public abstract double prever(List<Double> medicoes);

	// Verifica se existem medições válidas.
	protected void validarMedicoes(List<Double> medicoes) {

		// Não permite listas vazias ou nulas.
		if (medicoes == null || medicoes.isEmpty()) {
			throw new DadosInvalidosException("É necessário informar medições para realizar a previsão.");
		}

		// Verifica se todos os valores são válidos.
		for (Double valor : medicoes) {

			if (valor == null || !Double.isFinite(valor) || valor < 0) {

				throw new DadosInvalidosException("As medições devem conter apenas valores válidos e não negativos.");
			}
		}
	}

	// Retorna o nome da estratégia.
	public String getNome() {
		return nome;
	}
}
