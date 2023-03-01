package com.example.NetLivros.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.example.NetLivros.mapper.LivroMapper;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;

@Service
public class LivroService {

	private final LivroRepository livroRepository;
	private final AutorRepository autorRepository;
	private final LivroMapper mapper;

	public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, LivroMapper mapper) {
		this.livroRepository = livroRepository;
		this.autorRepository = autorRepository;
		this.mapper = mapper;
	}

	public LivroDTO save(Long autorId, LivroDTO livroDTO) {
		Livro livro = mapper.toLivro(livroDTO);

		livroRepository.save(livro);

		livroDTO = mapper.toLivroDTO(livro);

		return livroDTO;
	}

	public List<LivroDTO> findAll(String titulo, Integer numeroDePaginas, Double preco, String genero, String editora) {

		Livro livro = new Livro(titulo, numeroDePaginas, preco, genero, editora);
		Example<Livro> example = Example.of(livro, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING));
		List<Livro> livros = livroRepository.findAll(example);
		List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
		return livrosDTO;
	}

	public LivroDTO findById(Long id) {
		Livro livro = livroRepository.findById(id).orElseThrow(RuntimeException::new);
		LivroDTO livroDTO = mapper.toLivroDTO(livro);
		return livroDTO;
	}

	public List<LivroDTO> findByAutor(String nomeAutor) {
		Autor autor = autorRepository.findByNome(nomeAutor);
		List<Livro> livros = autor.getLivros();
		List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
		return livrosDTO;
	}

	public List<LivroDTO> findAllByGenero(String genero) {
		List<Livro> livros = livroRepository.findAllByGenero(genero);
		List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
		return livrosDTO;
	}

	public List<LivroDTO> findAllByEditora(String editora) {
		List<Livro> livros = livroRepository.findAllByEditora(editora);
		List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
		return livrosDTO;
	}

	public LivroDTO findByTitulo(String titulo) {
		Livro livro = livroRepository.findByTitulo(titulo).orElseThrow(RuntimeException::new);
		LivroDTO livroDTO = mapper.toLivroDTO(livro);
		return livroDTO;
	}

	public List<LivroDTO> findAllDinamico(Livro livro) {
		Example<Livro> example = Example.of(livro);
		List<Livro> livros = livroRepository.findAll(example);
		List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
		return livrosDTO;
	}

	public LivroDTO update(Long id, LivroDTO livroDTO) {
		Livro livro = livroRepository.findById(id).orElseThrow(RuntimeException::new);
		livroDTO.setId(id);
		livro = mapper.toLivro(livroDTO);
		livroRepository.save(livro);
		return livroDTO;
	}

	public void deleteById(Long id) {
		livroRepository.deleteById(id);
	}

}
