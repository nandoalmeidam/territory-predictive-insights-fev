package br.com.pluvia.model;

import br.com.pluvia.exception.DadosInvalidosException;

// Armazena os limites de chuva de uma região monitorada.
public class DadosTerritorio {

	// Limites encapsulados (em milímetros).
	private double limiteAtencao;
	private double limiteAlerta;
	private double limiteCritico;

	// Construtor com limites iniciais simulados.
	public DadosTerritorio() {
		configurarLimites(20, 50, 80);
	}

	// Permite configurar os três limites com validação.
	public void configurarLimites(double atencao, double alerta, double critico) {

		// Verifica se os valores são números válidos.
		if (!Double.isFinite(atencao) || !Double.isFinite(alerta) || !Double.isFinite(critico)) {

			throw new DadosInvalidosException("Os limites devem ser números válidos.");
		}

		// Não permite limites negativos.
		if (atencao < 0 || alerta < 0 || critico < 0) {
			throw new DadosInvalidosException("Os limites não podem ser negativos.");
		}

		// Os limites precisam estar em ordem crescente.
		if (atencao >= alerta || alerta >= critico) {
			throw new DadosInvalidosException("Os limites devem seguir: ATENCAO < ALERTA < CRITICO.");
		}

		// Atualiza somente depois de todas as validações.
		this.limiteAtencao = atencao;
		this.limiteAlerta = alerta;
		this.limiteCritico = critico;
	}

	// Getters: permitem consultar os limites.
	public double getLimiteAtencao() {
		return limiteAtencao;
	}

	public double getLimiteAlerta() {
		return limiteAlerta;
	}

	public double getLimiteCritico() {
		return limiteCritico;
	}
}
