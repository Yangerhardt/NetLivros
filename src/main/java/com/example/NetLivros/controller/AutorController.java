package com.example.NetLivros.controller;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @GetMapping
    public List<AutorDTO> read() {
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
    public ResponseEntity<AutorDTO> update(@Valid @PathVariable Long id, @RequestBody Autor autor) {
        return AutorService.update(id, autor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Autor> delete(@PathVariable Long id) {
        return AutorService.delete(id);
    }
}
