package com.example.NetLivros.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.service.AutorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Api de Autores")
@RestController
@RequestMapping("/api/autores")
public class AutorController {

	private final AutorService service;

	public AutorController(AutorService service) {
		this.service = service;
	}

	@GetMapping
	@ApiOperation("Obter lista de autores")
	public ResponseEntity<List<AutorDTO>> read() {
		return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
	}

	@GetMapping("/{id}")
	@ApiOperation("Obter autor por id")
	public ResponseEntity<AutorDTO> readById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
	}

	@PostMapping
	@ApiOperation("Cria novo autor")
	public ResponseEntity<AutorDTO> create(@RequestBody @Valid AutorDTO autor) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(autor));
	}

	@PutMapping("/{id}")
	@ApiOperation("Atualiza autor existente")
	public ResponseEntity<AutorDTO> update(@PathVariable Long id, @RequestBody @Valid AutorDTO autor) {
		return ResponseEntity.status(HttpStatus.OK).body(service.update(id, autor));
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Deleta autor existente")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}
}
