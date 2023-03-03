package com.example.NetLivros.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.NetLivros.model.Livro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LivroDTO {

	private Long id;
	@NotBlank
	private String titulo;
	@NotNull
	private Integer numeroDePaginas;
	@NotNull
	private Double preco;
	@NotBlank
	private String genero;
	@NotBlank
	private String editora;
	@NotNull
	private Long autorId;

	public LivroDTO(Livro livro) {
		this.id = livro.getId();
		this.titulo = livro.getTitulo();
		this.numeroDePaginas = livro.getNumeroDePaginas();
		this.preco = livro.getPreco();
		this.genero = livro.getGenero();
		this.editora = livro.getEditora();
		this.autorId = livro.getAutor().getId();
	}

	
}
