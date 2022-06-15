package es.codeurjc.apirestgestionusuarios.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.apirestgestionusuarios.NuevoUsuario;
import es.codeurjc.apirestgestionusuarios.model.Usuario;
import es.codeurjc.apirestgestionusuarios.repository.RepoUsuario;


@Service
public class UserService {
	
	@Autowired
	private RepoUsuario repository;
	public UserService(RepoUsuario repository) {
		this.repository=repository;
	}
	
	public Optional<Usuario> findOne(Long id){
		return repository.findById(id);
	}
	
	public boolean exist(Long id) {
		return repository.existsById(id);
	}
	public Optional<Usuario> findOne(long id) {
		return repository.findById(id);
	}

	public List<Usuario> findAll() {
		return repository.findAll();
	}
	
	public Usuario save (Usuario user) {
		Usuario newUser = repository.save(user);
		return newUser;
	}
	public void editarNombre (long id,String name) {
		repository.updateNombreById(id, name);
	
		
	}
	public Usuario editarUsuario(Usuario u, NuevoUsuario NewUsuario){
		if (NewUsuario.getName()!=null||NewUsuario.getName().equals("string")==false) 
			u.setName(NewUsuario.getName());
		if(NewUsuario.getLogin()!=null||NewUsuario.getLogin().equals("string")==false)
			u.setLogin(NewUsuario.getLogin());
		if(NewUsuario.getContraseña()!=null||NewUsuario.getContraseña().equals("string")==false)
			u.setContraseña(NewUsuario.getContraseña());
		if(NewUsuario.getSaldo()!=0)
			u.setSaldo(NewUsuario.getSaldo());
		return u;
		
	}
	public void editarActivo (long id,String Estado) {
		repository.updateEstadoById(id, Estado);
	}
	public void editarContraseña (long id,String pass) {
		repository.updateContraseñaById(id, pass);
	
	}
	public void editarApellido (long id,String ape) {
		repository.updateApellidoById(id, ape);
	}


	public void delete(Long Id) {
		repository.deleteById(Id);
	}
}
