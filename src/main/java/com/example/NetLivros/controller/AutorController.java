package com.example.NetLivros.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.service.AutorService;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

	private final AutorService service;
	
	
	
    public AutorController(AutorService service) {
		this.service = service;
	}

	@GetMapping
    public ResponseEntity<List<AutorDTO>> read() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> readById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<AutorDTO> create(@RequestBody @Valid AutorDTO autor) {
        return service.save(autor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> update(@Valid @PathVariable Long id, @RequestBody AutorDTO autor) {
        return service.update(id, autor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
