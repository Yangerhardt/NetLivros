package com.example.NetLivros.mock;

import java.util.ArrayList;
import java.util.List;

import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;

public class MocksLivro {

	public static final Livro LIVRO_1 = Livro.builder().titulo("Titulo 1").genero("Genero 1").editora("Editora 1")
			.numeroDePaginas(100).preco(100.0).autor(MocksAutor.AUTOR_1).build();
	public static final Livro LIVRO_2 = Livro.builder().titulo("Titulo 2").genero("Genero 2").editora("Editora 2")
			.numeroDePaginas(200).preco(200.0).autor(MocksAutor.AUTOR_2).build();
	public static final Livro LIVRO_3 = Livro.builder().titulo("Titulo 3").genero("Genero 3").editora("Editora 3")
			.numeroDePaginas(300).preco(300.0).autor(MocksAutor.AUTOR_3).build();
	public static Livro INVALID_LIVRO = Livro.builder().titulo("").genero("").editora("").numeroDePaginas(null)
			.preco(null).autor(null).build();

	public static List<Livro> LIVROS = new ArrayList<Livro>() {
		private static final long serialVersionUID = 1L;
		{
			add(LIVRO_1);
			add(LIVRO_2);
			add(LIVRO_3);
		}
	};
	public static LivroDTO LIVRO_DTO_1 = LivroDTO.builder().titulo("Titulo 1").genero("Genero 1").editora("Editora 1")
			.numeroDePaginas(100).preco(100.0).autorId(1L).build();
	public static LivroDTO LIVRO_DTO_2 = LivroDTO.builder().titulo("Titulo 2").genero("Genero 2").editora("Editora 2")
			.numeroDePaginas(200).preco(200.0).autorId(2L).build();
	public static LivroDTO LIVRO_DTO_3 = LivroDTO.builder().titulo("Titulo 3").genero("Genero 3").editora("Editora 3")
			.numeroDePaginas(300).preco(300.0).autorId(3L).build();
	public static LivroDTO INVALID_LIVRO_DTO = LivroDTO.builder().titulo("").genero("").editora("")
			.numeroDePaginas(null).preco(null).autorId(null).build();
	public static List<LivroDTO> LIVROS_DTO = new ArrayList<LivroDTO>() {
		private static final long serialVersionUID = 1L;
		{
			add(LIVRO_DTO_1);
			add(LIVRO_DTO_2);
			add(LIVRO_DTO_3);
		}
	};
}
