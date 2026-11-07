package br.com.pluvia.sensor;

// Contrato que todos os sensores do Pluvia devem seguir.
public interface Sensor {

	// Realiza uma leitura simulada do sensor.
	double lerValor();

	// Verifica se o sensor está funcionando.
	boolean estaFuncionando();

	// Retorna o tipo do sensor.
	String getTipo();
}
