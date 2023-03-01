package com.example.NetLivros.controller;

import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/livros")
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> readAll(@RequestParam(required = false) String titulo,
          @RequestParam(required = false) Integer numeroDePaginas, @RequestParam(required = false) Double preco,
          @RequestParam(required = false) String genero, @RequestParam(required = false) String editora) {
        return service.findAll(titulo, numeroDePaginas, preco, genero, editora);
    }

//    @GetMapping("/id/{id}")
//    public ResponseEntity<LivroDTO> readById (@PathVariable Long id) {
//        return LivroService.findById(id);
//    }

    @PostMapping("/{autorId}")
    public ResponseEntity<LivroDTO> create (@PathVariable Long autorId, @RequestBody LivroDTO livroDTO) {
        return LivroService.save(autorId, livroDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> update (@PathVariable Long id, @RequestBody LivroDTO livroDTO) {
        return LivroService.update(id, livroDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        return LivroService.delete(id);
    }

}
