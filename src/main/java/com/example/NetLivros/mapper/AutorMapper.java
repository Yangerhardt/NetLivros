package com.example.NetLivros.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.AutorDTO;

@Component
public class AutorMapper {

	private LivroMapper mapper;

	public List<AutorDTO> toAutorDTOList(List<Autor> autors) {
		return autors.stream().map(l -> new AutorDTO(l)).toList();
	}

	public AutorDTO toAutorDTO(Autor autor) {
		AutorDTO dto = new AutorDTO(autor);
		return dto;
	}

	public Autor toAutor(AutorDTO autorDTO) {
		Autor autor = new Autor();
		autor.setId(autorDTO.getId());
		autor.setNome(autorDTO.getNome());
		List<Livro> livros = autorDTO.getLivrosDTO().stream().map(livroDTO -> mapper.toLivro(livroDTO)).toList();
		autor.setLivros(livros);

		return autor;
	}

}
