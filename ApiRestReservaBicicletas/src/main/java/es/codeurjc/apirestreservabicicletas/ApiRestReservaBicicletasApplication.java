package es.codeurjc.apirestreservabicicletas;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApiRestReservaBicicletasApplication {

	private String direccion="http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/";
	public static void main(String[] args) {
		SpringApplication.run(ApiRestReservaBicicletasApplication.class, args);
		ApiRestReservaBicicletasApplication op = new ApiRestReservaBicicletasApplication();
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
