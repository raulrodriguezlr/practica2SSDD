package es.codeurjc.apirestgestionusuarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import es.codeurjc.apirestgestionusuarios.usuarios.*;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component

public class DBInitializer {
	
	
	
	@Autowired
	private UserService User;
	@Autowired
	private RepoUsuario UserRepo;
	
	
	@PostConstruct
	public void userDB() {
		
		UserRepo.saveAll(Arrays.asList(
				new Usuario("Raul"," Rodriguez", "Qwerty123456",20),
				new Usuario("Daniel"," Requena", "123456Qwerty",20),
				new Usuario("David"," Gomez", "SSDD2021/2022",20),
				new Usuario("Oscar"," Soto", "SSDDApoyo2021/2022",15),
				new Usuario("alonso"," Vazquez", ".-SSDD.-",5)
				));
	}
	}

