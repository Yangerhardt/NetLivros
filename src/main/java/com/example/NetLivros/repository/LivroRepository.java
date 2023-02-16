package com.example.NetLivros.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.NetLivros.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
//    Object findByAutor(String autor);

	List<Livro> findByAutor(String autor);

//    Object findByEditora(String editora);
//
//    Object findByTitulo(String titulo);
	
	Optional<Livro> findByTitulo(String titulo);

	List<Livro> findAllByGenero(String genero);

	List<Livro> findAllByEditora(String genero);
}
