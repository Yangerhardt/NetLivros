package com.example.NetLivros.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.service.LivroService;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

	@GetMapping
	public ResponseEntity<List<LivroDTO>> read() {
		return LivroService.findAll();
	}

	@GetMapping("/id/{id}") // Conferir
	public ResponseEntity<LivroDTO> readById(@PathVariable Long id) {
		return LivroService.findById(id);
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<LivroDTO> readByTitulo(@PathVariable String titulo) {
		return LivroService.findByTitulo(titulo);
	}

	@GetMapping("/autor/{autor}")
	public ResponseEntity<List<LivroDTO>> readByAutor(@PathVariable String autor) {
		return LivroService.findByAutor(autor);
	}

	@GetMapping("/editora/{editora}")
	public ResponseEntity<List<LivroDTO>> readByEditora(@PathVariable String editora) {
		return LivroService.findByEditora(editora);
	}

	@GetMapping("/genero/{genero}")
	public ResponseEntity<List<LivroDTO>> readByGenero(@PathVariable String genero) {
		return LivroService.findAllByGenero(genero);
	}

	@PostMapping("/{autorId}")
	public ResponseEntity<LivroDTO> create(@PathVariable Long autorId, @RequestBody LivroDTO livroDTO) {
		return LivroService.save(autorId, livroDTO);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<LivroDTO> update(@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
		return LivroService.update(id, livroDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		return LivroService.delete(id);
	}

}
