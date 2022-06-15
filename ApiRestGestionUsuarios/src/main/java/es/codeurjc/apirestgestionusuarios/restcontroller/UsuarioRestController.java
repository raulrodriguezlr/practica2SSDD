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

import es.codeurjc.apirestgestionusuarios.MostrarUsuario;
import es.codeurjc.apirestgestionusuarios.NuevoUsuario;
import es.codeurjc.apirestgestionusuarios.model.Usuario;
import es.codeurjc.apirestgestionusuarios.service.UserService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {
	@Autowired
	private UserService users;
	
	@GetMapping("/")
	@Operation(summary = "Devuelve todos los usuarios")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Found users", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MostrarUsuario.class)) })
	
	,@ApiResponse(responseCode = "404", description = "Users not found", content = @Content) })
	public List<MostrarUsuario> getUsuarios(){
		
		List<Usuario> usuarios=users.findAll();
		List<MostrarUsuario> mostrarUsuarios= new ArrayList<>();
		for(Usuario aux:usuarios) {
			mostrarUsuarios.add(new MostrarUsuario(aux.getId(),aux.getLogin(),aux.getName(),aux.getFechaAlta(),aux.getEstado(),aux.getSaldo()));
		}
		return mostrarUsuarios;
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Devuelve el usuario con el ID adecuado")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Found the ID", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = MostrarUsuario.class)) })
	,@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content)
	,@ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
	public ResponseEntity<MostrarUsuario> getUsuario(@PathVariable long id){
		Optional<Usuario> usuario = users.findOne(id);
		Usuario aux;
		if (usuario != null) {
			aux =usuario.get();
			return ResponseEntity.ok(new MostrarUsuario(aux.getId(),aux.getLogin(),aux.getName(),aux.getFechaAlta(),aux.getEstado(),aux.getSaldo()));
		}
		else
			return ResponseEntity.notFound().build();
		
	}
	@PostMapping("/")
	@Operation(summary = "Crea el usuario que se especifica en el body")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "201", description = "User created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
	,@ApiResponse(responseCode = "400", description = "Invalid parameters supplied", content = @Content)})
	public ResponseEntity<Usuario> createUser(@RequestBody  NuevoUsuario user){
		Usuario nuevoUsuario = new Usuario(user.login,user.name,user.contraseña,user.saldo);
		users.save(nuevoUsuario);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(nuevoUsuario.getId()).toUri();
		return ResponseEntity.created(location).body(nuevoUsuario);
	}
	
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Pone Inactivo el usuario con el ID indicado")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Found the ID", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
	,@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content)
	,@ApiResponse(responseCode = "404", description = "User not found", content = @Content) })
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
	@Operation(summary = "actualiza los parametros que se especifiquen del usuario con el ID indicado,"
			+ "si no se indica nuevos parametros, se preservará los que estaban anteriormente")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Found the ID", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
	,@ApiResponse(responseCode = "201", description = "Succesful user update", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
	,@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content)
	,@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	,@ApiResponse(responseCode = "422", description = "nuevos parametros invalidos", content = @Content)})
	public ResponseEntity<Usuario> replaceUsuario(@PathVariable long id, @RequestBody NuevoUsuario user){
		Optional<Usuario> usuario = users.findOne(id);
		Usuario u;
		if(usuario.isPresent()) {
			u=usuario.get();
			users.save(users.editarUsuario(u, user));
			
			return ResponseEntity.ok(u);
		}
		else
			return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/pagos/{id}")
	@Operation(summary = "Descuenta 3 euros, 1.5 de reserva y 1.5 de finaza , del saldo del usuario con el ID indicado")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Found the ID", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
	,@ApiResponse(responseCode = "201", description = "Succesful payment", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
	,@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content)
	,@ApiResponse(responseCode = "404", description = "User not found", content = @Content)
	,@ApiResponse(responseCode = "500", description = "not enough cash", content = @Content)})
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
	@Operation(summary = "Añade 1.5 euros de la fianza al saldo del usuario (indicado por el id),una vez devuelta la bici")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Found the ID", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
	,@ApiResponse(responseCode = "201", description = "Succesful payment", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) })
	,@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content)
	,@ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
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
