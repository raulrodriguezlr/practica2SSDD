package es.codeurjc.apirestreservabicicletas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import es.codeurjc.apirestreservabicicletas.model.Bicicleta;
import es.codeurjc.apirestreservabicicletas.model.Estacion;
import es.codeurjc.apirestreservabicicletas.repository.RepoBicicletas;


@Service
public class BicicletaService {
	
	@Autowired
	private RepoBicicletas repository;
	
	public BicicletaService(RepoBicicletas repository) {
		this.repository=repository;
	}
	
	public Optional<Bicicleta> findOne(Long id){
		return repository.findById(id);
	}
	
	public boolean exist(Long id) {
		return repository.existsById(id);
	}

	public Optional<Bicicleta> findOne(long id) {
		return repository.findById(id);
	}
	
	public List<Bicicleta> findAll() {
		return repository.findAll();
	}

	public void editarEstado(long id,String Estado) {
		repository.updateEstadoById(id, Estado);
	}
	
	public Bicicleta save (Bicicleta bici) {
		Bicicleta BiciNueva = repository.save(bici);
		return BiciNueva;
	}
	
	public void delete(Long Id) {
		repository.deleteById(Id);
	}
	public void establecerEstacion (long id,Estacion estacion) {
		repository.updateEstacionById(id, estacion);
	
		
	}
}
