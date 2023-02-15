package com.example.NetLivros.repository;

import com.example.NetLivros.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Object findByAutor(String autor);

    Object findByEditora(String editora);

    Object findByTitulo(String titulo);

    Object findByGenero(String genero);
}
