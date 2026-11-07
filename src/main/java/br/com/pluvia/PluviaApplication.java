package br.com.pluvia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import br.com.pluvia.menu.MenuConsole;

// Classe principal da aplicação Pluvia.
@SpringBootApplication
public class PluviaApplication {

	public static void main(String[] args) {

		// Cria a aplicação Spring Boot.
		SpringApplication app = new SpringApplication(PluviaApplication.class);

		// Permite abrir janelas gráficas do Java Swing.
		// Necessário para o JFileChooser (Salvar como).
		app.setHeadless(false);

		// Inicia o Spring Boot.
		app.run(args);
	}

	// Executa o menu após a inicialização do Spring.
	@Bean
	@ConditionalOnProperty(name = "pluvia.menu.enabled", havingValue = "true", matchIfMissing = true)
	CommandLineRunner iniciarMenu(MenuConsole menu) {

		return args -> {
			menu.iniciar();
		};
	}
}
