package com.example.NetLivros.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.service.LivroService;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

	private final LivroService service;

	public LivroController(LivroService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<LivroDTO>> readAll(@RequestParam(required = false) String titulo,
			@RequestParam(required = false) Integer numeroDePaginas, @RequestParam(required = false) Double preco,
			@RequestParam(required = false) String genero, @RequestParam(required = false) String editora) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.findAll(titulo, numeroDePaginas, preco, genero, editora));
	}

	@PostMapping("/{autorId}")
	public ResponseEntity<LivroDTO> create(@PathVariable Long autorId, @RequestBody LivroDTO livroDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(autorId, livroDTO));
	}

	@PutMapping("/{id}")
	public ResponseEntity<LivroDTO> update(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(service.update(id, livroDTO));
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		service.deleteById(id);
		ResponseEntity.status(HttpStatus.NO_CONTENT);
	}

}
