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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.codeurjc.apirestreservabicicletas.Ids;

import es.codeurjc.apirestreservabicicletas.model.Bicicleta;
import es.codeurjc.apirestreservabicicletas.model.Estacion;
import es.codeurjc.apirestreservabicicletas.service.BicicletaService;
import es.codeurjc.apirestreservabicicletas.service.EstacionService;



@RestController
@RequestMapping("/api/reservas")
public class ReservaRestController {

@Autowired
private BicicletaService bservice;
@Autowired
private EstacionService eservice;


	@PutMapping("/")
	public ResponseEntity<Bicicleta> reservar(@RequestBody Ids ids) {
		try {
		Optional<Estacion> e =eservice.findOne(ids.getId_estacion());
		Optional<Bicicleta>b =bservice.findOne(ids.getId_bici());
		if(e!=null&&b!=null) {
			Estacion estacion=e.get();
			Bicicleta bici=b.get();
			RestTemplate restTemplate = new RestTemplate();
			String url ="http://localhost:8081/api/usuarios/"+ids.getId_usuario();
			ObjectNode data = restTemplate.getForObject(url, ObjectNode.class);
			double saldoActual =data.get("saldo").asDouble();
			if(saldoActual>=3) {
				if(estacion.getEstado().equals("ACTIVO")&&bici.getEstacion().getId()==estacion.getId()&&bici.getEstado().equals("En-Base")) {
					Double NuevoSaldo =data.get("saldo").asDouble()-3;
				
					b.get().setEstado("RESERVADA");
					b.get().setEstacion(null);
					e.get().borrarBici(b.get());
					eservice.save(e.get());
					bservice.save(b.get());
					data.put("saldo",NuevoSaldo );
					restTemplate.put(url,data);
					
					URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
					return ResponseEntity.created(location).build();
				}
				else
					return ResponseEntity.unprocessableEntity().build();
			}else
				System.out.println("\n no hay saldo suficiente");
		}else
			return ResponseEntity.notFound().build();
		}catch(NullPointerException ex) {System.out.println("\nNo se ha podido encontrar esa bici en esa estacion o bien los ids son nulos");}
		return null; 
	}
	
	@PutMapping("/devoluciones")
	public ResponseEntity<Bicicleta> liberar(@RequestBody Ids ids){
		Optional<Estacion> e =eservice.findOne(ids.getId_estacion());
		Optional<Bicicleta>b =bservice.findOne(ids.getId_bici());
		if(e!=null&&b!=null) {
			Estacion estacion=e.get();
			Bicicleta bici=b.get();
			if(estacion.getEstado().equals("ACTIVO")&&bici.getEstado().equals("RESERVADA")) {
				if(estacion.getCapacidad()>estacion.getBicis().size()) {
					
					RestTemplate restTemplate = new RestTemplate();
					String url ="http://localhost:8081/api/usuarios/"+ids.getId_usuario();
					ObjectNode data = restTemplate.getForObject(url, ObjectNode.class);
					Double NuevoSaldo =data.get("saldo").asDouble()+1.5;
					b.get().setEstado("En-Base");
					b.get().setEstacion(e.get());
					e.get().add(b.get());
					eservice.save(e.get());
					bservice.save(b.get());
					data.put("saldo",NuevoSaldo );
					restTemplate.put(url,data);
					
					URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
						return ResponseEntity.created(location).build();
					}
				else
					throw new NullPointerException("La capacidad esta llena");
				}
			else {
				return ResponseEntity.unprocessableEntity().build();
			}
		}
		else
			return ResponseEntity.notFound().build();
		
	}
	
	/*ESTO DE ACONTINUACION ES PARA DEBUGGEAR NO SE PIDE*/
	@GetMapping("/bicis")
	public List<Bicicleta> getBicis(){
		
		return bservice.findAll();
	}
	@GetMapping("/estaciones")
	public List<Estacion> getEstaciones(){
		
		return eservice.findAll();
	}
	@GetMapping("/{id}")
	public ResponseEntity<Bicicleta> getBici(@PathVariable long id){
		
		Optional<Bicicleta> usuario = bservice.findOne(id);
		if (usuario != null) 
			return ResponseEntity.ok(usuario.get());
		else
			return ResponseEntity.notFound().build();
		
		
	}
}
