package com.example.NetLivros.mapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.service.AutorService;


@Component
public class LivroMapper {

	private final AutorService service;

	public LivroMapper(AutorService service) {
		this.service = service;
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
		AutorDTO autorDTO = service.findById(livroDTO.getAutorId());
		Autor autor = new Autor();
		BeanUtils.copyProperties(autorDTO, autor);
		
		List<Livro> livros = autor.getLivros();
		
		autor.setLivros(livros);

		livro.setAutor(autor);

		return livro;
	}

}
