package com.borathings.borapagar.ping;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe que servirá de exemplo para como seguir os padrões do projeto
 */

@RestController
public class PingController {

	/**
	 * HTTPHandler que ao receber um GET em "/ping", retornará a string "pong" com status OK (200)
	 * @see PingController
	 */
	@GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong");
    }
}
