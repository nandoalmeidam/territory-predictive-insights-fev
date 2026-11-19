
package br.com.pluvia.sensor;

import java.util.Random;

import br.com.pluvia.exception.FonteIndisponivelException;

// Sensor responsável por simular a medição de chuva em milímetros.
public class SensorChuva implements Sensor {

    private static final double VALOR_MAXIMO = 100.0;

    private final Random random = new Random();

    // Define se o sensor está operacional.
    private boolean funcionando = true;

    // Realiza uma leitura simulada entre 0 e 100 mm.
    @Override
    public double lerValor() {

        if (!estaFuncionando()) {
            throw new FonteIndisponivelException(
                "O sensor de chuva está indisponível."
            );
        }

        return random.nextDouble() * VALOR_MAXIMO;
    }

    // Verifica a disponibilidade do sensor.
    @Override
    public boolean estaFuncionando() {
        return funcionando;
    }

    // Retorna o tipo do sensor.
    @Override
    public String getTipo() {
        return "Pluviometro";
    }

    // Altera o estado operacional do sensor.
    public void setFuncionando(boolean funcionando) {
        this.funcionando = funcionando;
    }
}
