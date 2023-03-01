package com.example.NetLivros.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Livro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    @NotNull
    private Integer numeroDePaginas;
    @NotNull
    private Double preco;
    @NotNull
    private String genero;
    @NotEmpty @NotNull
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
