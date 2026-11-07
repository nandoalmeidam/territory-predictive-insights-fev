package br.com.pluvia.previsao;

import java.util.List;
import br.com.pluvia.exception.DadosInvalidosException;

// Compara as medições recentes com as anteriores.
public class PrevisaoComparacao extends EstrategiaPrevisao {
	// Construtor.
	public PrevisaoComparacao() {
		super("Previsao por Comparacao");
	}

	// Calcula uma estimativa baseada na tendência das medições.
	@Override
	public double prever(List<Double> medicoes) {
		// Validação herdada da classe abstrata.
		validarMedicoes(medicoes);
		// Precisamos de pelo menos duas medições.
		if (medicoes.size() < 2) {
			throw new DadosInvalidosException("A previsão por comparação exige pelo menos duas medições.");
		}
		// Divide o histórico em dois períodos.
		int metade = medicoes.size() / 2;
		double somaAnterior = 0;
		double somaRecente = 0;
		// Soma as medições do período anterior.
		for (int i = 0; i < metade; i++) {
			somaAnterior += medicoes.get(i);
		}
		// Soma as medições do período recente.
		for (int i = metade; i < medicoes.size(); i++) {
			somaRecente += medicoes.get(i);
		}
		// Calcula a média de cada período.
		double mediaAnterior = somaAnterior / metade;
		double mediaRecente = somaRecente / (medicoes.size() - metade);
		// Calcula a diferença entre os períodos.
		double diferenca = mediaRecente - mediaAnterior;
		// Projeta a tendência, sem permitir resultado negativo.
		return Math.max(0, mediaRecente + diferenca);
	}
}
