package es.codeurjc.apirestreservabicicletas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.apirestreservabicicletas.model.Estacion;
import es.codeurjc.apirestreservabicicletas.repository.RepoEstacion;



@Service
public class EstacionService{
	
	@Autowired
	private RepoEstacion repository;
	public EstacionService(RepoEstacion repository) {
		this.repository=repository;
	}
	
	public Optional<Estacion> findOne(Long id){
		return repository.findById(id);
	}
	
	public boolean exist(Long id) {
		return repository.existsById(id);
	}
	public Optional<Estacion> findOne(long id) {
		return repository.findById(id);
	}

	public List<Estacion> findAll() {
		return repository.findAll();
	}
	
	public Estacion save(Estacion estacion) {
		Estacion newEstacion = repository.save(estacion);
		return newEstacion;
	}
	public void editarCoordenadas(long id,String coord) {
		repository.updateCoordenadasById(id, coord);
	}
	public void editarActivo(long id,String Estado) {
		repository.updateEstadoById(id, Estado);
	}
	public void delete(Long Id) {
		repository.deleteById(Id);
	}
}
