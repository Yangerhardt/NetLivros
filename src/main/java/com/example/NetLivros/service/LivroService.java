package com.example.NetLivros.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.example.NetLivros.exception.ResourceNotFoundException;
import com.example.NetLivros.mapper.LivroMapper;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;

@Service
public class LivroService {

	private final AutorRepository autorRepository;
	private final LivroRepository livroRepository;
	private final LivroMapper mapper;

	private Logger logger = LoggerFactory.getLogger(LivroService.class);

	public LivroService(AutorRepository autorRepository, LivroRepository livroRepository, LivroMapper mapper) {
		this.autorRepository = autorRepository;
		this.livroRepository = livroRepository;
		this.mapper = mapper;
	}

	public LivroDTO save(Long autorId, LivroDTO livroDTO) {
		livroDTO.setAutorId(autorId);
		Livro livro = mapper.toLivro(livroDTO);
		Autor autor = autorRepository.findById(autorId)
				.orElseThrow(() -> new ResourceNotFoundException("Autor não Encontrado"));
		livro.setAutor(autor);

		livroRepository.save(livro);

		livroDTO = mapper.toLivroDTO(livro);
		logger.info("Salvando Livro no Banco de Dados");

		return livroDTO;
	}

	public List<LivroDTO> findAll(String titulo, Integer numeroDePaginas, Double preco, String genero, String editora) {

		Livro livro = new Livro(titulo, numeroDePaginas, preco, genero, editora);
		Example<Livro> example = Example.of(livro, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING));
		List<Livro> livros = livroRepository.findAll(example);
		List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
		logger.info("Lendo Livros do Banco de Dados");

		return livrosDTO;
	}

	public LivroDTO findById(Long id) {
		Livro livro = livroRepository.findById(id).orElseThrow(RuntimeException::new);
		LivroDTO livroDTO = mapper.toLivroDTO(livro);
		logger.info("Buscando Livro Por ID no Banco de Dados");

		return livroDTO;
	}

	public LivroDTO update(Long id, LivroDTO livroDTO) {
		Livro livro = livroRepository.findById(id).orElseThrow(RuntimeException::new);
		livroDTO.setAutorId(livro.getAutor().getId());
		livroDTO.setId(id);
		livro = mapper.toLivro(livroDTO);
		livroRepository.save(livro);

		logger.info("Atualizando Livro Por ID no Banco de Dados");
		livroDTO = mapper.toLivroDTO(livro);

		return livroDTO;
	}

	public void deleteById(Long id) {
		livroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado!"));
		logger.info("Deletando Livro Por ID no Banco de Dados");
		livroRepository.deleteById(id);
	}

}
