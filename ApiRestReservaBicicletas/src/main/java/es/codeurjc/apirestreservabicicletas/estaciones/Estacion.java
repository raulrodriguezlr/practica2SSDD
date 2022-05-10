package es.codeurjc.apirestreservabicicletas.estaciones;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.codeurjc.apirestreservabicicletas.bicicletas.Bicicleta;

@Entity
public class Estacion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	public int numeroSerie;
	public String coordenadas;
	
	public int capacidad;
	public String estado;
	public String fechaInstalacion;
	@JsonIgnore
	@OneToMany(mappedBy="estacion")
	public List<Bicicleta> bicis;
	public Estacion () {}
	public Estacion(int numeroSerie, String coordenadas,int capacidad) {
		super();
		this.numeroSerie = numeroSerie; 
		this.coordenadas=coordenadas;
		this.capacidad=capacidad;
		bicis=new ArrayList<Bicicleta>();

		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		this.fechaInstalacion = dtf.format(LocalDateTime.now());
		this.estado="ACTIVO";	
	}
	
	public List<Bicicleta> getBicis(){
		return bicis;
	}
	
	public void eliminarBici(Bicicleta bici) {
		try {
			int posicion = bicis.indexOf(bici);
			if (posicion!=-1) {
				bicis.remove(posicion);
			}
		}catch(NullPointerException ex) {
			System.out.println("NullPointerException");
		}
	}
	
	public boolean estacionLlena() {
		return capacidad==bicis.size();
	}
	
	public boolean estacionVacia() {
		return bicis.size()==0;
	}
	
	public void setNumeroSerie(int numSerie) {
		this.numeroSerie=numSerie;
	}
	public void setSCoordenadas(String coordenadas) {
		this.coordenadas=coordenadas;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad=capacidad;
	}
	public void setEstado(String estado) {
		this.estado=estado;
	}
	public int getNumeroSerie() {
		return this.numeroSerie;
	}
	public String getCoordenadas() {
		return this.coordenadas;
	}
	public int getCapacidad() {
		return this.capacidad;
	}
	public String getEstado() {
		return this.estado;
	}
	public long getId() {
		return id;
	}
	
	

}
