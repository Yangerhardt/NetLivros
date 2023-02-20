package com.example.NetLivros.repository;

import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Object findByAutor(String autor);

    Optional<Livro> findByEditora(String editora);

    Optional<Livro> findByTitulo(String titulo);

    Object findByGenero(String genero);
}
