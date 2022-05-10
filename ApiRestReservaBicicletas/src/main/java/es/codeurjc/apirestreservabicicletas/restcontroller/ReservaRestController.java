package es.codeurjc.apirestreservabicicletas.restcontroller;

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
//import es.codeurjc.apirestgestionusuarios.usuarios.Usuario;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;


import es.codeurjc.apirestreservabicicletas.bicicletas.*;
import es.codeurjc.apirestreservabicicletas.estaciones.EstacionService;



@RestController
@RequestMapping("/api/reserve")
public class ReservaRestController {

@Autowired
private BicicletaService bservice;
@Autowired
private EstacionService eservice;

/*
	@PutMapping("/")
	public List<String> usuarios(){
		
		
		
		RestTemplate restTemplate = new RestTemplate();
		String url ="https://localhost:8081/api/users/";
		
		ObjectNode[] dataNodes = restTemplate.getForObject(url, ObjectNode[].class);
		
		
		return uNombres;
	}
	*/
	@GetMapping("/")
	public List<Bicicleta> getUsuarios(){
		
		return bservice.findAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Bicicleta> getUsuario(@PathVariable long id){
		
		Optional<Bicicleta> usuario = bservice.findOne(id);
		if (usuario != null) 
			return ResponseEntity.ok(usuario.get());
		else
			return ResponseEntity.notFound().build();
		
		
	}
}
