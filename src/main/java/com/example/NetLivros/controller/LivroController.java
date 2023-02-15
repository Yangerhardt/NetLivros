package com.example.NetLivros.controller;

import com.example.NetLivros.model.Livro;
import com.example.NetLivros.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/livros")
public class LivroController {

    @GetMapping
    public ResponseEntity<Iterable<Livro>> read () {
        return LivroService.findAll();
    }

    @GetMapping("/id/{id}") // Conferir
    public ResponseEntity<Livro> readById (@PathVariable Long id) {
        return LivroService.findById(id);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Object> readByTitulo (@PathVariable String titulo) {
        return LivroService.findByTitulo(titulo);
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<Object>> readByAutor (@PathVariable String autor) {
        return LivroService.findByAutor(autor);
    }

    @GetMapping("/editora/{editora}")
    public ResponseEntity<List<Object>> readByEditora (@PathVariable String editora) {
        return LivroService.findByEditora(editora);
    }

    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<Object>> readByGenero (@PathVariable String genero) {
        return LivroService.findByGenero(genero);
    }

    @PostMapping
    public ResponseEntity<Livro> create (@RequestBody Livro livro) {
        return LivroService.save(livro);
    }

    @PutMapping("{id}")
    public ResponseEntity<Livro> update (@PathVariable Long id, @RequestBody Livro livro) {
        return LivroService.update(id, livro);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        return LivroService.delete(id);
    }

}
