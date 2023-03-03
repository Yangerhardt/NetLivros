package com.example.NetLivros.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 50, nullable = false, unique = true)
	private String titulo;
	@Column(nullable = false)
	private Integer numeroDePaginas;
	@Column(nullable = false, precision = 5, scale = 2)
	private Double preco;
	@Column(length = 50, nullable = false)
	private String genero;
	@Column(length = 50, nullable = false)
	private String editora;
	@ManyToOne
	private Autor autor;

	public Livro(String titulo, Integer numeroDePaginas, Double preco, String genero, String editora) {
		this.titulo = titulo;
		this.numeroDePaginas = numeroDePaginas;
		this.preco = preco;
		this.genero = genero;
		this.editora = editora;
	}

}
