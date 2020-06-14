package fi.exadeci.imgcatalog;

import java.security.MessageDigest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ImgcatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImgcatalogApplication.class, args);
	}

	
	@Bean
	public MessageDigest getMD5MessageDigest() throws Exception {
		return MessageDigest.getInstance("MD5"); 
	}
	

}
