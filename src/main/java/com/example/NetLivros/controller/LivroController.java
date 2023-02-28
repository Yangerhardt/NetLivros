package com.example.NetLivros.controller;

import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/livros")
public class LivroController {

    @GetMapping
    public List<LivroDTO> read () {
        return LivroService.findAll();
    }

    @GetMapping("/id/{id}") // Conferir
    public ResponseEntity<LivroDTO> readById (@PathVariable Long id) {
        return LivroService.findById(id);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<LivroDTO> readByTitulo (@PathVariable String titulo) {
        return LivroService.findByTitulo(titulo);
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<LivroDTO>> readByAutor (@PathVariable String autor) {
        return LivroService.findByAutor(autor);
    }

    @GetMapping("/editora/{editora}")
    public ResponseEntity<List<LivroDTO>> readByEditora (@PathVariable String editora) {
        return LivroService.findByEditora(editora);
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<LivroDTO>> readByGenero (@PathVariable String genero) {
        return LivroService.findByGenero(genero);
    }

    @PostMapping("/{autorId}")
    public ResponseEntity<LivroDTO> create (@PathVariable Long autorId, @RequestBody LivroDTO livroDTO) {
        return LivroService.save(autorId, livroDTO);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<LivroDTO> update (@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
        return LivroService.update(id, livroDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        return LivroService.delete(id);
    }

}
