package com.example.NetLivros.model.dto;

import com.example.NetLivros.model.Livro;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LivroDTO {

    private Long id;
    private String titulo;
    private Double preco;
    private String genero;
    private String editora;
    private Integer numeroDePaginas;
    private Long autorId;

    public LivroDTO(Livro livro) {
        this.id = livro.getId();
        this.titulo = livro.getTitulo();
        this.preco = livro.getPreco();
        this.genero = livro.getGenero();
        this.editora = livro.getEditora();
        this.numeroDePaginas = livro.getNumeroDePaginas();
        this.autorId = livro.getAutor().getId();
    }

}
