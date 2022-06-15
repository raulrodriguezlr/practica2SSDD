package es.codeurjc.apirestgestionusuarios.model;
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
	
	public double saldo;
	
	
	public Usuario() {}
	
	public Usuario(String login,String name,String contraseña,double saldo) {
		super();
		this.login=login;
		this.name = name; 
		
		this.contraseña=contraseña;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.fechaAlta = dtf.format(LocalDateTime.now());
		this.estado="ACTIVO";
		this.saldo=saldo;
				
	}
	//SETTERS
	public void setLogin(String login) {
		this.login=login;
	}
	public void setName(String nombreNuevo) {
		
		this.name=nombreNuevo;
	}
	public void setEstado(String estado) {
		
		this.estado=estado;
	}
	
	public void setContraseña(String contraseña) {
		this.contraseña=contraseña;
	}
	public void setSaldo(double saldo) {
		this.saldo=saldo;
	}
	
	
	
	//GETTERS
	public String getLogin() {
		return this.login;
	}
	public String getName() {
		return this.name;
	}
	public String getFechaAlta() {
		return this.fechaAlta;
	}
	public void setFechaAlta(String fecha) {
		this.fechaAlta=fecha;
	}
	
	public String getContraseña() {
		return this.contraseña;
	}
	public String getEstado() {
		return this.estado;
	}
	public double getSaldo() {
		return this.saldo;
	}
	public long getId() {
		return this.id;
	}
}