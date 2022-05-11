package es.codeurjc.apirestreservabicicletas;


import java.util.Arrays;
import java.util.List;




import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import es.codeurjc.apirestreservabicicletas.bicicletas.*;

import es.codeurjc.apirestreservabicicletas.estaciones.*;

@Component

public class DBInitializer {
	
	
	
	

	@Autowired
	private RepoBicicletas BicicletasRepo;

	@Autowired
	private RepoEstacion EstacionRepo;

	
	@PostConstruct
	public void userDB() {
		
	
	
		
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
		Estacion e1= new Estacion(123401,"40.336726, -3.875893", 5);
		Estacion e2= new Estacion(123402,"40.335498, -3.874363", 10);
		Estacion e3=new Estacion(123403,"40.334339, -3.878446", 5);
		List<Bicicleta> bici = BicicletasRepo.findAll();
		
		e1.add(bici.get(0));
		e1.add(bici.get(1));
		e1.add(bici.get(2));
		
		e2.add(bici.get(4));
		e2.add(bici.get(5));
		e2.add(bici.get(6));
		EstacionRepo.saveAll(Arrays.asList(e1,e2,e3
			));
		
		
		List<Estacion> estaciones= EstacionRepo.findAll();
		BicicletasRepo.updateEstacionById(bici.get(0).getId_b(), estaciones.get(0));
		BicicletasRepo.updateEstacionById(bici.get(1).getId_b(), estaciones.get(0));
		BicicletasRepo.updateEstacionById(bici.get(2).getId_b(), estaciones.get(0));
		BicicletasRepo.updateEstacionById(bici.get(3).getId_b(), estaciones.get(0));
		BicicletasRepo.updateEstacionById(bici.get(4).getId_b(), estaciones.get(1));
		BicicletasRepo.updateEstacionById(bici.get(5).getId_b(), estaciones.get(1));
		BicicletasRepo.updateEstacionById(bici.get(6).getId_b(), estaciones.get(1));
		
		
		
		
		
		
		}	
	
	}

