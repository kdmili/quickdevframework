package org.lm.jpa.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntityRestController {
	public ResponseEntity<Object> list(){return new ResponseEntity<>(HttpStatus.OK);}
	
}
