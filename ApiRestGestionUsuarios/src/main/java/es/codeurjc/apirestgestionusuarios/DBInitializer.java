package es.codeurjc.apirestgestionusuarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import es.codeurjc.apirestgestionusuarios.model.Usuario;
import es.codeurjc.apirestgestionusuarios.repository.RepoUsuario;
import es.codeurjc.apirestgestionusuarios.service.UserService;



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
				new Usuario("raulrodriguezlr","Raul Rodriguez", "Qwerty123456",20),
				new Usuario("requelgns"," Daniel Requena", "123456Qwerty",20),
				new Usuario("David0101"," David Gomez", "SSDD2021/2022",20),
				new Usuario("Oscar2122"," Oscar Soto", "SSDDApoyo2021/2022",15),
				new Usuario("alongazo"," Alonso Vazquez", ".-SSDD.-",5)
				));
	}
	}

