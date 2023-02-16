package com.example.NetLivros.model.dto;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.example.NetLivros.model.Autor;

public class AutorDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String nome;
	private List<LivroDTO> livrosDTO;
	
	

	public AutorDTO(Autor autor) {
		super();
		this.id = autor.getId();
		this.nome = autor.getNome();
		this.livrosDTO = autor.getLivros().stream().map(livro -> new LivroDTO(livro)).toList();
	}
	
	public AutorDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<LivroDTO> getLivrosDTO() {
		return livrosDTO;
	}

	public void setLivrosDTO(List<LivroDTO> livrosDTO) {
		this.livrosDTO = livrosDTO;
	}

}
