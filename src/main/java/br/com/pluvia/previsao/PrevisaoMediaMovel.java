package br.com.pluvia.previsao;

import java.util.List;

// Estratégia que calcula a média das últimas medições.
public class PrevisaoMediaMovel extends EstrategiaPrevisao {

	// Quantidade máxima de medições utilizadas.
	private int quantidadeMedicoes;

	// Construtor.
	public PrevisaoMediaMovel(int quantidadeMedicoes) {

		// Chama o construtor da classe mãe.
		super("Previsao por Media Movel");

		// Define a quantidade de medições.
		if (quantidadeMedicoes <= 0) {
			throw new IllegalArgumentException("A quantidade de medições deve ser maior que zero.");
		}

		this.quantidadeMedicoes = quantidadeMedicoes;
	}

	// Implementa o cálculo da previsão.
	@Override
	public double prever(List<Double> medicoes) {

		// Reutiliza a validação da classe mãe.
		validarMedicoes(medicoes);

		// Define quantas medições serão utilizadas.
		int quantidade = Math.min(quantidadeMedicoes, medicoes.size());

		double soma = 0;

		// Soma as últimas medições da lista.
		for (int i = medicoes.size() - quantidade; i < medicoes.size(); i++) {

			soma += medicoes.get(i);
		}

		// Retorna a média calculada.
		return soma / quantidade;
	}

	// Retorna a quantidade configurada.
	public int getQuantidadeMedicoes() {
		return quantidadeMedicoes;
	}
}
