package es.codeurjc.apirestgestionusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan({"es.codeurjc.apirestgestionusuario"})
//@EntityScan({"es.codeurjc.apirestgestionusuario.model"})
//@EnableJpaRepositories("es.codeurjc.apirestgestionusuario.repository")

public class ApiRestGestionUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiRestGestionUsuariosApplication.class, args);
	}

}
