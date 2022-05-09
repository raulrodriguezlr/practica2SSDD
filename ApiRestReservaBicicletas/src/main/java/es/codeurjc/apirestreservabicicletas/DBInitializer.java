package es.codeurjc.apirestreservabicicletas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;



import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import es.codeurjc.apirestreservabicicletas.bicicletas.*;

import es.codeurjc.apirestreservabicicletas.estaciones.*;

@Component
@Profile("local")
public class DBInitializer {
	
	
	
	

	@Autowired
	private RepoBicicletas BicicletasRepo;

	@Autowired
	private RepoEstacion EstacionRepo;

	
	@PostConstruct
	public void userDB() {
		
	
	
		EstacionRepo.saveAll(Arrays.asList(
			new Estacion(123401,"40.336726, -3.875893", 5),
			new Estacion(123402,"40.335498, -3.874363", 10),
			new Estacion(123403,"40.334339, -3.878446", 5)
			));
		BicicletasRepo.saveAll(Arrays.asList(
				new Bicicleta("0O1P2Q3R4S5T6U7V","MOUNTAIN","En-Base"),
				new Bicicleta("0A1B2C3D4E5F6G7H","BMX","En-Base"),
				new Bicicleta("ZQRF54TH99PLF90T","MOUNTAIN","En-Base"),
				new Bicicleta("XXAGER746DRGF90P","STREET","En-Base"),
				new Bicicleta("V4L543ETINA9044M","MOUNTAIN","En-Base"),
				new Bicicleta("BHUFBHD74BC545VB","BMX","En-Base"),
				new Bicicleta("DAFT74FDFF66F90P","STREET","En-Base"),
				new Bicicleta("ZID904FBSFSANE10","STREET"),
				new Bicicleta("ASTUCHD74BC545VB","BMX")
				));
		
		List<Bicicleta> bici = BicicletasRepo.findAll();
		List<Estacion> estaciones= EstacionRepo.findAll();
		BicicletasRepo.updateEstacionById(bici.get(0).getId(), estaciones.get(0));
		BicicletasRepo.updateEstacionById(bici.get(1).getId(), estaciones.get(0));
		BicicletasRepo.updateEstacionById(bici.get(2).getId(), estaciones.get(0));
		BicicletasRepo.updateEstacionById(bici.get(3).getId(), estaciones.get(0));
		BicicletasRepo.updateEstacionById(bici.get(4).getId(), estaciones.get(1));
		BicicletasRepo.updateEstacionById(bici.get(5).getId(), estaciones.get(1));
		BicicletasRepo.updateEstacionById(bici.get(6).getId(), estaciones.get(1));
		
		
		
		
		}	
	
	}
}
