package com.example.NetLivros.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;

@Service
public class LivroService {

	public static LivroRepository livroRepository;
	public static AutorRepository autorRepository;

	public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
		LivroService.livroRepository = livroRepository;
		LivroService.autorRepository = autorRepository;
	}

	public static ResponseEntity<LivroDTO> save(Long autorId, LivroDTO livroDTO) {
		Autor autor = autorRepository.findById(autorId).orElseThrow(() -> new RuntimeException("Autor não encontrado"));
		Livro livro = new Livro();
		BeanUtils.copyProperties(livroDTO, livro);
		livro.setAutor(autor);

		livroRepository.save(livro);
		BeanUtils.copyProperties(livro, livroDTO);
		livroDTO.setAutorId(autorId);

		return ResponseEntity.status(HttpStatus.CREATED).body(livroDTO);
	}

	public static ResponseEntity<List<LivroDTO>> findAll() {
		List<Livro> livros = livroRepository.findAll();
		List<LivroDTO> livrosDTO = livros.stream().map(livro -> new LivroDTO(livro)).toList();
		return ResponseEntity.ok().body(livrosDTO);
	}

	public static ResponseEntity<LivroDTO> findById(Long id) {
		Livro livro = livroRepository.findById(id).orElseThrow(RuntimeException::new);
		LivroDTO livroDTO = new LivroDTO(livro);
		return ResponseEntity.ok().body(livroDTO);
	}

	public static ResponseEntity<List<LivroDTO>> findByAutor(String nomeAutor) {
		Autor autor = autorRepository.findByNome(nomeAutor);
		List<LivroDTO> livrosDTO = autor.getLivros().stream().map(livro -> new LivroDTO(livro)).toList();
		return ResponseEntity.ok().body(livrosDTO);
	}

	public static ResponseEntity<List<LivroDTO>> findAllByGenero(String genero) {
		List<LivroDTO> livrosDTO = livroRepository.findAllByGenero(genero).stream().map(livro -> new LivroDTO(livro))
				.toList();
		return ResponseEntity.ok().body(livrosDTO);
	}

	public static ResponseEntity<List<LivroDTO>> findByEditora(String editora) {
		List<LivroDTO> livrosDTO = livroRepository.findAllByEditora(editora).stream().map(livro -> new LivroDTO(livro))
				.toList();

		return ResponseEntity.ok().body(livrosDTO);
	}

	public static ResponseEntity<LivroDTO> findByTitulo(String titulo) {
		Livro livro = livroRepository.findByTitulo(titulo).orElseThrow(RuntimeException::new);
		LivroDTO livroDTO = new LivroDTO(livro);
		return ResponseEntity.ok().body(livroDTO);
	}

	public static ResponseEntity<LivroDTO> update(Long id, LivroDTO livroDTO) {
		Livro livro = livroRepository.findById(id).orElseThrow(RuntimeException::new);

		livro.setId(id);
		livroRepository.save(livro);
		livroDTO = new LivroDTO(livro);

		return ResponseEntity.ok().body(livroDTO);
	}

	public static ResponseEntity<Void> delete(Long id) {
		livroRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
