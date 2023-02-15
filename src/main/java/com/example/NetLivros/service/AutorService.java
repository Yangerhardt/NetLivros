package com.example.NetLivros.service;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.repository.AutorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    public static AutorRepository autorRepository;

    public AutorService(AutorRepository repository) {
        AutorService.autorRepository = repository;
    }

    public static ResponseEntity<Autor> save (Autor autor) {
        autorRepository.save(autor);
        return ResponseEntity.ok().body(autor);
    }

    public static List<Autor> findAll() {
        return autorRepository.findAll();
    }

    public static ResponseEntity<Autor> findById(Long id) {
        Optional<Autor> autor = autorRepository.findById(id);
        return autor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static ResponseEntity<Autor> update(Long id, Autor autor) {
        Autor aut = autorRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        aut.setNome(autor.getNome());
        aut.setLivros(autor.getLivros());

        autorRepository.save(aut);
        return ResponseEntity.ok().body(aut);
    }

    public static ResponseEntity<Autor> delete(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        autorRepository.delete(autor);
        return ResponseEntity.ok().build();
    }
}
