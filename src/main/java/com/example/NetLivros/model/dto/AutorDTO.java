package com.example.NetLivros.model.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.example.NetLivros.mapper.AutorMapper;
import com.example.NetLivros.model.Autor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data 
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
}
