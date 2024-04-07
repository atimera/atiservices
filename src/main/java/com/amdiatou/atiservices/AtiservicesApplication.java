package com.amdiatou.atiservices;

import com.amdiatou.atiservices.ville.Ville;
import com.amdiatou.atiservices.ville.VilleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class AtiservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtiservicesApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(VilleRepository villeRepository){
		return args -> {
			List<Ville> villes = List.of(new Ville(1, "Rossy-en-Brie", 77680, "SEINE-ET-MARNE"),
					new Ville(2, "Pontault-combault", 77340, "SEINE-ET-MARNE"));

			villeRepository.saveAll(villes);
		};
	}


}
