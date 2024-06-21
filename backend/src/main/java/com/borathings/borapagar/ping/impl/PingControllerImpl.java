package com.borathings.borapagar.ping.impl;

import java.util.Collections;
import java.util.List;

import com.borathings.borapagar.ping.PingController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementação do @PingController, Controller utilizado para fins de demonstração dos padrões a serem seguidos
 */
@RestController
public class PingControllerImpl implements PingController {
    /**
     * @return Uma response com código HTTP 200 e o body "pong"
     */
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong");
    }

    /**
     *
     * @param quantity quantidade de "pongs" que serão retornados
     * @return Uma response com código HTTP 200 e o body com uma lista de `quantity` pongs
     */
    public ResponseEntity<List<String>> pings(@RequestParam int quantity) {
        List<String> response = Collections.nCopies(quantity, "pong");
        return ResponseEntity.ok().body(response);
    }

}
