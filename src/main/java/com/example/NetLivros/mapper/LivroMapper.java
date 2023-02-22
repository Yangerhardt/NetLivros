package com.example.NetLivros.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;

@Component
public class LivroMapper {

	private final AutorRepository repository;

	public LivroMapper(AutorRepository repository) {
		this.repository = repository;
	}

	public List<LivroDTO> toLivroDTOList(List<Livro> livros) {
		return livros.stream().map(l -> new LivroDTO(l)).toList();
	}

	public LivroDTO toLivroDTO(Livro livro) {
		LivroDTO dto = new LivroDTO(livro);
		return dto;
	}

	public Livro toLivro(LivroDTO livroDTO) {
		Livro livro = new Livro();
		livro.setId(livroDTO.getId());
		livro.setTitulo(livroDTO.getTitulo());
		livro.setNumeroDePaginas(livroDTO.getNumeroDePaginas());
		livro.setPreco(livroDTO.getPreco());
		livro.setGenero(livroDTO.getGenero());
		livro.setEditora(livroDTO.getEditora());
		Autor autor = repository.findById(livroDTO.getAutorId())
				.orElseThrow(() -> new RuntimeException("Autor não encontrado!"));
		livro.setAutor(autor);

		return livro;
	}

}
