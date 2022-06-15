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


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import es.codeurjc.apirestreservabicicletas.Ids;

import es.codeurjc.apirestreservabicicletas.model.Bicicleta;
import es.codeurjc.apirestreservabicicletas.model.Estacion;
import es.codeurjc.apirestreservabicicletas.service.BicicletaService;
import es.codeurjc.apirestreservabicicletas.service.EstacionService;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.*;



@RestController
@RequestMapping("/api/reservas")
public class ReservaRestController {

@Autowired
private BicicletaService bservice;
@Autowired
private EstacionService eservice;

	@PutMapping("/")
	@Operation(summary = "Reserva a bici al usuario indicado por el id de usuario, pone la bici en estado"
			+ " reservada y saca la bici de la estacion")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Found the ID", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Bicicleta.class)),
			@Content(mediaType = "application/json", schema = @Schema(implementation = Estacion.class))})
	,@ApiResponse(responseCode = "201", description = "Succesful reserva", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Bicicleta.class)),
			@Content(mediaType = "application/json", schema = @Schema(implementation = Estacion.class)) })
	,@ApiResponse(responseCode = "400", description = "Invalid ids supplied", content = @Content)
	,@ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
	,@ApiResponse(responseCode = "422", description = "Bike not in this station", content = @Content)
	,@ApiResponse(responseCode = "500", description = "Internal server error ", content = @Content)})
	public ResponseEntity<Bicicleta> reservar1(@RequestBody Ids ids) {
		try {
			Optional<Estacion> e =eservice.findOne(ids.getId_estacion());
			Optional<Bicicleta>b =bservice.findOne(ids.getId_bici());
			if(e!=null&&b!=null) {
				Estacion estacion=e.get();
				Bicicleta bici=b.get();
				RestTemplate restTemplate = new RestTemplate();
				String url ="http://localhost:8081/api/usuarios/pagos/"+ids.getId_usuario();
				restTemplate.put(url, ObjectNode.class);
				if(estacion.getEstado().equals("ACTIVO")&&bici.getEstacion().getId()==estacion.getId()&&bici.getEstado().equals("En-Base")) {
					eservice.save(eservice.reservar(bici, estacion));
					bservice.save(bservice.reservar(bici));
					URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
					return ResponseEntity.created(location).build();
				}else
					return ResponseEntity.unprocessableEntity().build();
			}else
				return ResponseEntity.notFound().build();
		}catch(NullPointerException ex) {
			System.out.println("\nNo se ha podido encontrar esa bici en esa estacion o bien los ids son nulos");
			return ResponseEntity.notFound().build();
		}
		
	}
	@Operation(summary = "Devuelve el dinero de la fianza al usuario indicado por su id, además"
			+ ", asigna la bici(con el id_bici) a la estacion indicada(id_estacion) siempre y cuando haya espacio y cambia su estado")
	@ApiResponses(value = {
	@ApiResponse(responseCode = "200", description = "Found the ID", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Bicicleta.class)),
			@Content(mediaType = "application/json", schema = @Schema(implementation = Estacion.class))})
	,@ApiResponse(responseCode = "201", description = "Succesful devolucion", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Bicicleta.class)),
			@Content(mediaType = "application/json", schema = @Schema(implementation = Estacion.class)) })
	,@ApiResponse(responseCode = "400", description = "Invalid ids supplied", content = @Content)
	,@ApiResponse(responseCode = "404", description = "Resource not found", content = @Content)
	,@ApiResponse(responseCode = "422", description = "Bike already on station", content = @Content)
	,@ApiResponse(responseCode = "500", description = "Internal server error,probable full Capacity ", content = @Content)})
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
					String url ="http://localhost:8081/api/usuarios/devoluciones/"+ids.getId_usuario();
					restTemplate.put(url, ObjectNode.class);
					bici.setEstado("En-Base");
					bici.setEstacion(estacion);
					estacion.add(bici);
					eservice.save(e.get());
					bservice.save(b.get());
					URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
					return ResponseEntity.created(location).build();
					}
				else
					throw new NullPointerException("La capacidad esta llena");
			}else 
				return ResponseEntity.unprocessableEntity().build();
		}else
			return ResponseEntity.notFound().build();
	}
	
	/*ESTO DE ACONTINUACION ES PARA DEBUGGEAR NO SE PIDE*/
	@Operation(summary = "Devuelve todas las bicis")
	@GetMapping("/bicis")
	public List<Bicicleta> getBicis(){
		
		return bservice.findAll();
	}
	@Operation(summary = "Devuelve todas las estaciones")
	@GetMapping("/estaciones")
	public List<Estacion> getEstaciones(){
		
		return eservice.findAll();
	}
	
}
