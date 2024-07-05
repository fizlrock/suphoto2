
 

package dev.fizlrock.suphoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.openapitools.api.UsersApi;

@SpringBootApplication
public class SuphotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuphotoApplication.class, args);
	}


	class someApi implements UsersApi{
		
	}

}
