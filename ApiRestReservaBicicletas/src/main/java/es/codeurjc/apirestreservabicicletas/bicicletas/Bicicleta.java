package es.codeurjc.apirestreservabicicletas.bicicletas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.codeurjc.apirestreservabicicletas.estaciones.Estacion;



@Entity
public class Bicicleta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long id_b;
	public String numero_serie;
	public String modelo;
	public String fechaAlta;
	public String estado;
	
	@JsonIgnore
	@ManyToOne
	public Estacion estacion;
	
public Bicicleta() {}
	
	public Bicicleta(String numeroSerie, String modelo) {
		super();
		this.modelo = modelo;
		this.numero_serie=numeroSerie;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.fechaAlta = dtf.format(LocalDateTime.now());
		this.estado="Sin-Base";
				
	}
	public Bicicleta(String numeroSerie, String modelo,String estado) {
		super();
		this.modelo = modelo;
		this.numero_serie=numeroSerie;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.fechaAlta = dtf.format(LocalDateTime.now());
		this.estado=estado;
				
	}
	public void setEstacion(Estacion estacion) {
		this.estacion=estacion;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numero_serie=numeroSerie;
	}
	
	public void setEstado(String estado) {
		this.estado=estado;
	}
	
	public void setModelo(String modelo) {
		this.modelo=modelo;
	}
	
	public long getId_b() {
		return id_b;
	}
	
	public String getNumero_serie() {
		return this.numero_serie;
	}
	
	public String getEstado() {
		return this.estado;
	}
	
	public String getModelo() {
		return this.modelo;
	}
	
	public Estacion getEstacion() {
		return this.estacion;
	}
	public String getFechaAlta() {
		return this.fechaAlta;
	}
	
	
	
}
