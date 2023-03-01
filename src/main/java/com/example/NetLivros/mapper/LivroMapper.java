package com.example.NetLivros.mapper;

import com.example.NetLivros.exceptions.RecursoNaoEncontradoException;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LivroMapper {

    public static LivroMapper mapper;
    private final AutorRepository repository;
    private final LivroRepository livroRepository;

    public LivroMapper(AutorRepository repository, LivroRepository livroRepository) {
        this.repository = repository;
        this.livroRepository = livroRepository;
    }

    public LivroDTO toLivroDTO(Livro livro) {
        return new LivroDTO(livro);
    }

    public List<LivroDTO> toLivroDTOList(List<Livro> livros) {
        return livros.stream().map(LivroDTO::new).collect(Collectors.toList());
    }

    public Livro toLivro(LivroDTO livroDTO) {
        Livro livro = new Livro();
        livro.setId(livroDTO.getId());
        livro.setTitulo(livroDTO.getTitulo());
        livro.setNumeroDePaginas(livroDTO.getNumeroDePaginas());
        livro.setPreco(livroDTO.getPreco());
        livro.setGenero(livroDTO.getGenero());
        livro.setEditora(livroDTO.getEditora());
        Autor autor = repository.findById(livroDTO.getAutorId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado."));
        livro.setAutor(autor);

        return livro;
    }

    public List<LivroDTO> findByEspecificacao (LivroDTO dto, String entrada) {
        List<Livro> livros = new ArrayList<>();

        livroRepository.findAll().forEach(livro -> {
            if (livro.getEditora().equals(entrada)) {
                livros.add(livro);
            }
        });

        return mapper.toLivroDTOList(livros);
    }
}
