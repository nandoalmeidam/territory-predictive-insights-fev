
package br.com.pluvia.sensor;

import java.util.Random;

import br.com.pluvia.exception.FonteIndisponivelException;

// Sensor responsável por simular o nível da água de um rio.
public class SensorNivelRio implements Sensor {

    private static final double NIVEL_MAXIMO = 5.0;

    private final Random random = new Random();

    // Define se o sensor está operacional.
    private boolean funcionando = true;

    // Realiza uma leitura simulada entre 0 e 5 metros.
    @Override
    public double lerValor() {

        if (!estaFuncionando()) {
            throw new FonteIndisponivelException(
                "O sensor de nível do rio está indisponível."
            );
        }

        return random.nextDouble() * NIVEL_MAXIMO;
    }

    // Verifica a disponibilidade do sensor.
    @Override
    public boolean estaFuncionando() {
        return funcionando;
    }

    // Retorna o tipo do sensor.
    @Override
    public String getTipo() {
        return "Nivel do Rio";
    }

    // Altera o estado operacional do sensor.
    public void setFuncionando(boolean funcionando) {
        this.funcionando = funcionando;
    }
}
