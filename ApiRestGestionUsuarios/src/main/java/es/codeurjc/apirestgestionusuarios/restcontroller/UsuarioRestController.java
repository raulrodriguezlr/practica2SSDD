package es.codeurjc.apirestgestionusuarios.restcontroller;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import es.codeurjc.apirestgestionusuarios.model.Usuario;
import es.codeurjc.apirestgestionusuarios.service.UserService;




@RestController
@RequestMapping("/api/usuarios")
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
		if(user.getEstado()==null)
			user.setActivo("ACTIVO");
		if(user.getFechaAlta()==null) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			user.setFecha( dtf.format(LocalDateTime.now()));
		}
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
			users.editarActivo(u.getId(),"INACTIVO");
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
			if (NewUsuario.getName()!=null) 
				u.setName(NewUsuario.getName());
			if(NewUsuario.getLogin()!=null)
				u.setLogin(NewUsuario.getLogin());
			if(NewUsuario.getContraseña()!=null)
				u.setPassword(NewUsuario.getContraseña());
			if(NewUsuario.getSaldo()!=0)
				u.setSaldo(NewUsuario.getSaldo());
			users.save(u);
			return ResponseEntity.ok(u);
		}
		else
			return ResponseEntity.notFound().build();
	}
	@PutMapping("/pagos/{id}")
	public ResponseEntity<Usuario> pago(@PathVariable long id){
		Optional<Usuario> u = users.findOne(id);
		Usuario usuario;
		if(u.isPresent()) {
			usuario=u.get();
			if(usuario.getEstado().equals("ACTIVO")&&usuario.getSaldo()>=3) {
				usuario.setSaldo(usuario.getSaldo()-3);//1,5 de pago y 1,5 de fianza
				users.save(usuario);
				
				return ResponseEntity.ok(u.get());
			}
			System.out.println("No se ha podido realizar el pago puesto que no hay dinero suficiente");
			return ResponseEntity.unprocessableEntity().build();
		}
		else
			return ResponseEntity.notFound().build();
		
	}
	@PutMapping("/devoluciones/{id}")
	public ResponseEntity<Usuario>devolucion(@PathVariable long id){
		Optional<Usuario> u = users.findOne(id);
		Usuario usuario;
		if(u.isPresent()) {
			usuario=u.get();
			usuario.setSaldo(usuario.getSaldo()+1.5);// 1,5 de fianza
			users.save(usuario);
			return ResponseEntity.ok(u.get());
		}
		else
			return ResponseEntity.notFound().build();
		
	}

	
	
}
