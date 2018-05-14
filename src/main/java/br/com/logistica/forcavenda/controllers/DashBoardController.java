package br.com.logistica.forcavenda.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dashboard")
public class DashBoardController {

  @GetMapping
  public ResponseEntity<?> home() {
    return new ResponseEntity<>("Bem vindo!", HttpStatus.OK);
  }
}
