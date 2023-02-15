package com.example.NetLivros.service;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.repository.AutorRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    public static AutorRepository repository;

    public AutorService(AutorRepository repository) {
        AutorService.repository = repository;
    }

    public static ResponseEntity<Autor> save (Autor autor) {
        repository.save(autor);
        return ResponseEntity.ok().body(autor);
    }

    public static List<Autor> findAll() {
        return repository.findAll();
    }

    public static ResponseEntity<Autor> findById(Long id) {
        Optional<Autor> autor = repository.findById(id);
        return autor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static ResponseEntity<Autor> update(Long id, Autor autor) {
        Autor aut = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        aut.setNome(autor.getNome());
        aut.setLivros(autor.getLivros());

        repository.save(aut);
        return ResponseEntity.ok().body(aut);
    }

    public static ResponseEntity<Autor> delete(Long id) {
        Autor autor = repository.findById(id)
                .orElseThrow(RuntimeException::new);

        repository.delete(autor);
        return ResponseEntity.ok().build();
    }
}
