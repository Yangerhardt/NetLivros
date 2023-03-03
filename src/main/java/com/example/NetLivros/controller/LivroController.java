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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.service.LivroService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Api("Api de Livros")
@RestController
@RequestMapping("/api/livros")
public class LivroController {

	private final LivroService service;

	@GetMapping
	@ApiOperation(value = "Obter lista de livros", response = LivroDTO.class)
	public ResponseEntity<List<LivroDTO>> readAll(@RequestParam(required = false) String titulo,
			@RequestParam(required = false) Integer numeroDePaginas, @RequestParam(required = false) Double preco,
			@RequestParam(required = false) String genero, @RequestParam(required = false) String editora) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.findAll(titulo, numeroDePaginas, preco, genero, editora));
	}
	
	@GetMapping("/preco")
	@ApiOperation(value = "Obter lista de livros por preco", response = LivroDTO.class)
	public ResponseEntity<List<LivroDTO>> readAllByPreco(@RequestParam(defaultValue = "0", required = false) Double min, @RequestParam Double max) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(service.findAllByPreco(min,max));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Obter livro por id", response = LivroDTO.class)
	public ResponseEntity<LivroDTO> readById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
	}

	@PostMapping("/{autorId}")
	@ApiOperation(value = "Cria novo livro, relacionado a um autor", response = LivroDTO.class, notes = "Livro não pode ser nulo")
	public ResponseEntity<LivroDTO> create(@PathVariable Long autorId, @RequestBody LivroDTO livroDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(autorId, livroDTO));
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza livro existente", response = LivroDTO.class, notes = "Livro não pode ser nulo")
	public ResponseEntity<LivroDTO> update(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
		return ResponseEntity.status(HttpStatus.OK).body(service.update(id, livroDTO));
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Deleta livro existente")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteById(@PathVariable Long id) {
		service.deleteById(id);
	}


}
