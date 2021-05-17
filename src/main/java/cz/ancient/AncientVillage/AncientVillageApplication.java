package cz.ancient.AncientVillage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AncientVillageApplication {

	public static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public static void main(String[] args) {
		SpringApplication.run(AncientVillageApplication.class, args);
	}
}
