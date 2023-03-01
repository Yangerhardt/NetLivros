package com.example.NetLivros.mapper;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.model.dto.LivroDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AutorMapper {

    public static AutorMapper autorMapper;
    private LivroMapper mapper;

    public List<AutorDTO> toAutorDTOList(List<Autor> autores) {
        return autores.stream().map(autor -> new AutorDTO(autor)).collect(Collectors.toList());
    }

    public AutorDTO toAutorDTO(Autor autor) {
        AutorDTO dto = new AutorDTO(autor);
        return dto;
    }

    public Autor toAutor(AutorDTO autorDTO) {
        Autor autor = new Autor();
        autor.setId(autorDTO.getId());
        autor.setNome(autorDTO.getNome());
        List<Livro> livros = autorDTO.getLivrosDTO().stream().map(livroDTO -> mapper.toLivro(livroDTO)).collect(Collectors.toList());
        autor.setLivros(livros);

        return autor;
    }

}
