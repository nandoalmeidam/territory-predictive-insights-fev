package br.com.pluvia.config;

import br.com.pluvia.exception.DadosInvalidosException;

// Singleton responsável pelas configurações do Pluvia.
public class ConfiguracaoSistema {
	// Única instância da classe.
	private static final ConfiguracaoSistema instancia = new ConfiguracaoSistema();
	private String nomeSistema;
	private int intervaloLeitura;

	// Construtor privado impede a criação de objetos externos.
	private ConfiguracaoSistema() {
		this.nomeSistema = "Pluvia - Monitoramento Inteligente de Chuvas";
		this.intervaloLeitura = 60;
	}

	// Retorna sempre a mesma instância.
	public static ConfiguracaoSistema getInstancia() {
		return instancia;
	}

	public String getNomeSistema() {
		return nomeSistema;
	}

	public int getIntervaloLeitura() {
		return intervaloLeitura;
	}

	// Permite configurar o intervalo entre leituras.
	public void setIntervaloLeitura(int intervaloLeitura) {
		if (intervaloLeitura <= 0) {
			throw new DadosInvalidosException("O intervalo de leitura deve ser maior que zero.");
		}
		this.intervaloLeitura = intervaloLeitura;
	}
}
