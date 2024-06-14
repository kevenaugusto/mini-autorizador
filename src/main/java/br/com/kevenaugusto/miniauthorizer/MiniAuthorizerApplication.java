package br.com.kevenaugusto.miniauthorizer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Mini Autorizador", version = "0.0.1", description = "Autorizador para transações de Vale Refeição e Vale Alimentação."))
public class MiniAuthorizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniAuthorizerApplication.class, args);
	}

}
