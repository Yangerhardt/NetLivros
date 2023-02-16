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

    @GetMapping
    public ResponseEntity<List<AutorDTO>> read() {
        return AutorService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> readById(@PathVariable Long id) {
        return AutorService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AutorDTO> create(@RequestBody @Valid AutorDTO autor) {
        return AutorService.save(autor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> update(@Valid @PathVariable Long id, @RequestBody AutorDTO autor) {
        return AutorService.update(id, autor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return AutorService.delete(id);
    }
}
