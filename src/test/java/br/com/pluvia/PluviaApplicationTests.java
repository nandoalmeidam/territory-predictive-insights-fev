package br.com.pluvia;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "pluvia.menu.enabled=false")
class PluviaApplicationTests {

	@Test
	void contextLoads() {
	}

}
