package com.example.NetLivros.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Livro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty @NotNull
    private String titulo;
    @NotNull
    private Integer numeroDePaginas;
    private Double preco;
    @NotEmpty @NotNull
    private String genero;
    @NotEmpty @NotNull
    private String editora;
    @ManyToOne
    private Autor autor;
}
