package br.com.pluvia.model;

import br.com.pluvia.exception.DadosInvalidosException;

// Representa uma região monitorada pelo Pluvia.
public class RegiaoMonitorada extends ComponenteTerritorial {

	// Volume de chuva registrado na região.
	private double volumeChuva;

	// Objeto que armazena os limites de risco.
	private DadosTerritorio dadosTerritorio;

	// Construtor da região monitorada.
	public RegiaoMonitorada(Long id, String nome, String regiao) {

		// Inicializa os atributos herdados.
		super(id, nome, regiao);

		// Inicia o volume de chuva em zero.
		this.volumeChuva = 0;

		// Cria a configuração de limites da região.
		this.dadosTerritorio = new DadosTerritorio();
	}

	// Verifica o risco usando os limites configurados.
	@Override
	public void verificarStatus() {

		if (volumeChuva >= dadosTerritorio.getLimiteCritico()) {
			setStatus("CRITICO");

		} else if (volumeChuva >= dadosTerritorio.getLimiteAlerta()) {
			setStatus("ALERTA");

		} else if (volumeChuva >= dadosTerritorio.getLimiteAtencao()) {
			setStatus("ATENCAO");

		} else {
			setStatus("NORMAL");
		}
	}

	// Consulta o volume de chuva.
	public double getVolumeChuva() {
		return volumeChuva;
	}

	// Atualiza o volume de chuva.
	public void setVolumeChuva(double volumeChuva) {

		// Impede valores negativos ou inválidos.
		if (!Double.isFinite(volumeChuva) || volumeChuva < 0) {
			throw new DadosInvalidosException("O volume de chuva deve ser um número válido e não negativo.");
		}

		this.volumeChuva = volumeChuva;

		// Atualiza automaticamente a classificação.
		verificarStatus();
	}

	// Permite consultar a configuração da região.
	public DadosTerritorio getDadosTerritorio() {
		return dadosTerritorio;
	}

	// Altera os limites de risco da região.
	public void configurarLimites(double atencao, double alerta, double critico) {

		// Valida e atualiza os limites.
		dadosTerritorio.configurarLimites(atencao, alerta, critico);

		// Recalcula o status com os novos limites.
		verificarStatus();
	}
}
