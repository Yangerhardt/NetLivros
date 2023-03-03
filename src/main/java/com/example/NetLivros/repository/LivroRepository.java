package com.example.NetLivros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.NetLivros.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
	List<Livro> findByAutor(String autor);
	Boolean existsByTitulo(String titulo);
	List<Livro> findByPrecoBetween(Double min, Double max);

}
