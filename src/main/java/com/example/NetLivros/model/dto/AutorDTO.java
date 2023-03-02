package com.example.NetLivros.model.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.example.NetLivros.mapper.AutorMapper;
import com.example.NetLivros.model.Autor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutorDTO {

	private Long id;
	@NotBlank
	private String nome;
	private List<LivroDTO> livrosDTO;

	public AutorDTO(Autor autor) {
		this.id = autor.getId();
		this.nome = autor.getNome();
		this.livrosDTO = AutorMapper.verifyngAndParseToLivrosDTO(autor);
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
