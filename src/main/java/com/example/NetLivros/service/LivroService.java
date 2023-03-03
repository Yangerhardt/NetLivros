package com.example.NetLivros.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.example.NetLivros.exception.ResourceAlreadyExistsException;
import com.example.NetLivros.exception.ResourceNotFoundException;
import com.example.NetLivros.mapper.LivroMapper;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class LivroService {

	private final AutorRepository autorRepository;
	private final LivroRepository livroRepository;
	private final LivroMapper mapper;

	public LivroDTO save(Long autorId, LivroDTO livroDTO) {
		if(livroRepository.existsByTitulo(livroDTO.getTitulo())) {
			throw new ResourceAlreadyExistsException("Livro já cadastrado!");
		}
		livroDTO.setAutorId(autorId);
		Livro livro = mapper.toLivro(livroDTO);
		Autor autor = autorRepository.findById(autorId)
				.orElseThrow(() -> new ResourceNotFoundException("Autor não Encontrado"));
		livro.setAutor(autor);

		livroRepository.save(livro);

		livroDTO = mapper.toLivroDTO(livro);
		log.info("Salvando Livro no Banco de Dados");

		return livroDTO;
	}

	public List<LivroDTO> findAll(String titulo, Integer numeroDePaginas, Double preco, String genero, String editora) {

		Livro livro = new Livro(titulo, numeroDePaginas, preco, genero, editora);
		Example<Livro> example = Example.of(livro, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING));
		List<Livro> livros = livroRepository.findAll(example);
		List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
		log.info("Lendo Livros do Banco de Dados");

		return livrosDTO;
	}

	public List<LivroDTO> findAllByPreco(Double min, Double max) {

		List<Livro> livros = livroRepository.findByPrecoBetween(min,max);
		List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
		log.info("Lendo Livros por Preços do Banco de Dados");

		return livrosDTO;
	}

	
	public LivroDTO findById(Long id) {
		Livro livro = livroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado!"));
		LivroDTO livroDTO = mapper.toLivroDTO(livro);
		log.info("Buscando Livro Por ID no Banco de Dados");

		return livroDTO;
	}

	public LivroDTO update(Long id, LivroDTO livroDTO) {
		Livro livro = livroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado!"));
		livroDTO.setAutorId(livro.getAutor().getId());
		livroDTO.setId(id);
		livro = mapper.toLivro(livroDTO);
		livroRepository.save(livro);

		log.info("Atualizando Livro Por ID no Banco de Dados");
		livroDTO = mapper.toLivroDTO(livro);

		return livroDTO;
	}

	public void deleteById(Long id) {
		livroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado!"));
		log.info("Deletando Livro Por ID no Banco de Dados");
		livroRepository.deleteById(id);
	}

}
