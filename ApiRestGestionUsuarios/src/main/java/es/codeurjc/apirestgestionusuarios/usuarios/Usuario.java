package es.codeurjc.apirestgestionusuarios.usuarios;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
public class Usuario implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public String login;
	
	public String contraseña;
	
	public String name;
	
	public String fechaAlta;
	public String estado;
	
	public int saldo;
	
	
	public Usuario() {}
	
	public Usuario(String login,String name,String contraseña) {
		super();
		this.login=login;
		this.name = name; 
		
		this.contraseña=contraseña;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.fechaAlta = dtf.format(LocalDateTime.now());
		this.estado="ACTIVO";
		this.saldo=0;
				
	}
	//SETTERS
	public void setLogin(String login) {
		this.login=login;
	}
	public void setName(String nombreNuevo) {
		
		this.name=nombreNuevo;
	}
	public void setActivo(String estado) {
		
		this.estado=estado;
	}
	
	public void setPassword(String contraseña) {
		this.contraseña=contraseña;
	}
	public void setSaldo(int saldo) {
		this.saldo=saldo;
	}
	
	
	
	//GETTERS
	public String getLogin() {
		return this.login;
	}
	public String getName() {
		return this.name;
	}
	
	public String getContraseña() {
		return this.contraseña;
	}
	public String getEstado() {
		return this.estado;
	}
	public int getSaldo() {
		return this.saldo;
	}
	public long getId() {
		return this.id;
	}
}