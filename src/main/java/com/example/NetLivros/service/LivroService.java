package com.example.NetLivros.service;

import com.example.NetLivros.exceptions.RecursoNaoEncontradoException;
import com.example.NetLivros.mapper.LivroMapper;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class LivroService {

    public static LivroRepository livroRepository;
    public static AutorRepository autorRepository;
    public static LivroMapper mapper = new LivroMapper(autorRepository, livroRepository);

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository, LivroMapper mapper) {
        LivroService.livroRepository = livroRepository;
        LivroService.autorRepository = autorRepository;
        LivroMapper.mapper = mapper;
    }

    public static ResponseEntity<LivroDTO> save (@Valid Long autorId, LivroDTO livroDTO) {
        Autor autor = autorRepository.findById(autorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado"));

        Livro livro = mapper.toLivro(livroDTO);
        livroRepository.save(livro);
        livroDTO = mapper.toLivroDTO(livro);

        return ResponseEntity.status(HttpStatus.CREATED).body(livroDTO);
    }

    public static ResponseEntity<List<LivroDTO>> findAll(String titulo, Integer numeroDePaginas, Double preco, String genero,
                                                         String editora) {

        Livro livro = new Livro(titulo, numeroDePaginas, preco, genero, editora);
        Example<Livro> example = Example.of(livro, ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        List<Livro> livros = livroRepository.findAll(example);
        List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<LivroDTO> findById (Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));
        LivroDTO livroDTO = mapper.toLivroDTO(livro);
        return ResponseEntity.ok().body(livroDTO);
    }

    public static ResponseEntity<List<LivroDTO>> findByAutor (String nomeAutor) {
        Autor autor = autorRepository.findByNome(nomeAutor);
        List<Livro> livros = autor.getLivros();
        List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);
        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<List<LivroDTO>> findByGenero (String genero) {
        List<Livro> livros = livroRepository.findAllByGenero(genero)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Genero não encontrado."));
        List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);

        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<List<LivroDTO>> findByEditora (String editora) {
        List<Livro> livros = livroRepository.findAllByEditora(editora)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Editora não encontrada."));
        List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);

        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<LivroDTO> findByTitulo (String titulo) {
        Livro livro = livroRepository.findByTitulo(titulo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));

        LivroDTO livroDTO = mapper.toLivroDTO(livro);
        return ResponseEntity.ok().body(livroDTO);
    }

    public static ResponseEntity<List<LivroDTO>> findByPreco (Double preco) {
        List<Livro> livros = new ArrayList<>();

        try {
            livroRepository.findAll().forEach(livro -> {
                if (livro.getPreco() <= preco) {
                    livros.add(livro);
                }
            });
        } catch (Exception e) {

        }

        List<LivroDTO> livrosDTO = mapper.toLivroDTOList(livros);

        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<LivroDTO> update (Long id, LivroDTO livroDTO) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));

        livroDTO.setAutorId(livro.getId());
        Livro novoLivro = mapper.toLivro(livroDTO);
        livroRepository.save(novoLivro);

        return ResponseEntity.ok().body(livroDTO);
    }

    public static ResponseEntity<Void> delete(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));

        livroRepository.deleteById(livro.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
