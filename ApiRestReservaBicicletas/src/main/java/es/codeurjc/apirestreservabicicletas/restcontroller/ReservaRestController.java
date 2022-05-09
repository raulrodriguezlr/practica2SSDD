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
import es.codeurjc.apirestgestionusuarios.usuarios.Usuario;



@RestController
@RequestMapping("/api/reserve")
public class ReservaRestController {

	@GetMapping("/usuarios")
	public List<Usuario> usuarios(){
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		RestTemplate restTemplate = new RestTemplate();
		String url ="https://localhost:8081/api/users/";
		Usuario[] users = restTemplate.getForObject(url, Usuario[].class);
		for (Usuario u : users) {
			listaUsuarios.add(u);
		}
		
		return listaUsuarios;
		
	}
}
