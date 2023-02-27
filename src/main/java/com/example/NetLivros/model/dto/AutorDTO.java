package com.example.NetLivros.model.dto;

import com.example.NetLivros.model.Autor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class AutorDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private List<LivroDTO> livrosDTO;

    public AutorDTO(Autor autor) {
        this.id = autor.getId();
        this.nome = autor.getNome();
        this.livrosDTO = (autor.getLivros() != null)
            ? autor.getLivros().stream().map(LivroDTO::new).collect(Collectors.toList())
            : null;
    }
}
