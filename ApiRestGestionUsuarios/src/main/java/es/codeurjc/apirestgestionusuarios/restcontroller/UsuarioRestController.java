package es.codeurjc.apirestgestionusuarios.restcontroller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.codeurjc.apirestgestionusuarios.usuarios.UserService;
import es.codeurjc.apirestgestionusuarios.usuarios.Usuario;

import es.codeurjc.apirestreservabicicletas.bicicletas.*;

import es.codeurjc.apirestreservabicicletas.estaciones.*;



@RestController
@RequestMapping("/api/users")
public class UsuarioRestController {
	@Autowired
	private UserService users;
	
	@GetMapping("/")
	public List<Usuario> getUsuarios(){
		
		return users.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getUsuario(@PathVariable long id){
		
		Optional<Usuario> usuario = users.findOne(id);
		if (usuario != null) 
			return ResponseEntity.ok(usuario.get());
		else
			return ResponseEntity.notFound().build();
		
		
	}
	@PostMapping("/")
	public ResponseEntity<Usuario> createUser(@RequestBody Usuario user){
		users.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(location).body(user);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Usuario> deleteuser(@PathVariable long id){
		Optional<Usuario> usuario = users.findOne(id);
		Usuario u ;
		if (usuario != null) {
			u=usuario.get();
			users.editarActivo(u.getId(),"Inactivo");
			return ResponseEntity.ok(u);
		}
		else
			return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> replaceUsuario(@PathVariable long id, @RequestBody Usuario NewUsuario){
		Optional<Usuario> usuario = users.findOne(id);
		Usuario u;
		if(usuario.isPresent()) {
			u=usuario.get();
			u.setName(NewUsuario.getName());
			u.setLogin(NewUsuario.getLogin());
			u.setPassword(NewUsuario.getContraseña());
			u.setSaldo(NewUsuario.getSaldo());
			users.save(u);
			return ResponseEntity.ok(u);
		}
		else
			return ResponseEntity.notFound().build();
	}
/*
	@PutMapping("/{id}/reserva")
	public ResponseEntity<Usuario> reserveBici(@PathVariable long id){
		List<Bicicleta> listaBicis = new ArrayList<Bicicleta>();
		RestTemplate restTemplate = new RestTemplate();
		String url ="https://localhost:8080/api/reserve/";
		Bicicleta[] bikes = restTemplate.getForObject(url, Bicicleta[].class);
		for (Bicicleta bicis : bikes) {
			
			
		}
	}
	*/
	
	
}
