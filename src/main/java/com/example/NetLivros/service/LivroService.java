package com.example.NetLivros.service;

import com.example.NetLivros.model.Livro;
import com.example.NetLivros.repository.LivroRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    public static LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        LivroService.livroRepository = livroRepository;
    }

    public static ResponseEntity<Livro> save (Livro livro) {
        livroRepository.save(livro);
        return ResponseEntity.ok().body(livro);
    }

    public static ResponseEntity<Livro> findById (Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static ResponseEntity<Void> delete(Long id) {
        livroRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public static ResponseEntity<Livro> update (Long id, Livro livro) {
        Livro liv = livroRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        liv.setTitulo(livro.getTitulo());
        liv.setAutor(livro.getAutor());
        liv.setGenero(livro.getGenero());
        liv.setEditora(livro.getEditora());
        liv.setNumeroDePaginas(livro.getNumeroDePaginas());

        return ResponseEntity.ok().body(livroRepository.save(liv));
    }

    public static ResponseEntity<Iterable<Livro>> findAll() {
        return ResponseEntity.ok().body(livroRepository.findAll());
    }

    public static ResponseEntity<List<Object>> findByAutor (String autor) {
        List<Object> autores = new ArrayList<>();

        livroRepository.findAll().forEach(livro -> {
            if (livro.getGenero().equals(autores)) {
                autores.add(livro);
            }
        });

        return ResponseEntity.ok().body(autores);
    }

    public static ResponseEntity<List<Object>> findByGenero (String genero) {
        List<Object> generos = new ArrayList<>();

        livroRepository.findAll().forEach(livro -> {
            if (livro.getGenero().equals(genero)) {
                generos.add(livro);
            }
        });

        return ResponseEntity.ok().body(generos);
    }

    public static ResponseEntity<List<Object>> findByEditora (String editora) {
        List<Object> livros = new ArrayList<>();

        livroRepository.findAll().forEach(livro -> {
            if (livro.getEditora().equals(editora)) {
                livros.add(livro);
            }
        });

        return ResponseEntity.ok().body(livros);
    }

    public static ResponseEntity<Object> findByTitulo (String titulo) {
        Object livro = livroRepository.findByTitulo(titulo);

        return ResponseEntity.ok().body(livro);
    }
}
