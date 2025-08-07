package com.journal.app.JournalApplication.Config;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI myCustomOpenAPI(){
        Server localServer = new Server()
                .url("http://localhost:8081")
                .description("Local development server");

        Server prodServer = new Server()
                .url("https://rocky-chamber-52878-7e1c1a4f72ab.herokuapp.com")
                .description("Heroku server - Production Server");

        return new OpenAPI().info
                (new Info().title("My Journal Application APIs").
                        description("This is the Open API specification for my Journal Application APIs"))
                .servers(Arrays.asList(localServer, prodServer));
    }
    /*@GetMapping("health-check")
    public ResponseEntity<?> healthCheck(){
        try {
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/
}
