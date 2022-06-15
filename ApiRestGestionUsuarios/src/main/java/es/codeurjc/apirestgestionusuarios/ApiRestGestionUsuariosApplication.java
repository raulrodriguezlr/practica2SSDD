package es.codeurjc.apirestgestionusuarios;

import java.io.IOException;

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

	private String direccion="http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/usuario-rest-controller";
	public static void main(String[] args) {
		SpringApplication.run(ApiRestGestionUsuariosApplication.class, args);
		ApiRestGestionUsuariosApplication op = new ApiRestGestionUsuariosApplication();
	     try {
			op.abrirNavegadorPorDefecto(op.direccion);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 public void abrirNavegadorPorDefecto(String url) throws IOException{
	        String osName = System.getProperty("os.name");
	        if(osName.contains("Windows"))
	            abrirNavegadorPredeterminadorWindows(url);
	        else if(osName.contains("Linux"))
	            abrirNavegadorPredeterminadorLinux(url);
	        else if(osName.contains("Mac OS X"))
	            abrirNavegadorPredeterminadorMacOsx(url);
	        else{ //INFORMAR SISTEMA NO SOPORTADO }
	    }
	 }
	 public void abrirNavegadorPredeterminadorWindows(String url) throws IOException{
	        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
	    }
	    public void abrirNavegadorPredeterminadorLinux(String url) throws IOException{
	        Runtime.getRuntime().exec("xdg-open " + url);
	    }
	    public void abrirNavegadorPredeterminadorMacOsx(String url) throws IOException{
	        Runtime.getRuntime().exec("open " + url);
	    }
	       

}
