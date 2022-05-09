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



	
	@PostConstruct
	public void userDB() {
		
		User.save(
			new Usuario("rulo1212","Raul Rodriguez", "Qwerty123456")
			
			
			);
	}
	}

