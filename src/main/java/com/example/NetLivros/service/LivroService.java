package com.example.NetLivros.service;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LivroService {

    public static LivroRepository livroRepository;
    public static AutorRepository autorRepository;

    public LivroService(LivroRepository livroRepository, AutorRepository autorRepository) {
        LivroService.livroRepository = livroRepository;
        LivroService.autorRepository = autorRepository;
    }

    public static ResponseEntity<LivroDTO> save (@Valid Long autorId, LivroDTO livroDTO) {
        Autor autor = autorRepository.findById(autorId).orElseThrow(() -> {
            return new RuntimeException("Autor não encontrado");
        });
        Livro livro = new Livro();
        BeanUtils.copyProperties(livroDTO, livro);
        livro.setAutor(autor);
        livroRepository.save(livro);

        BeanUtils.copyProperties(livro, livroDTO);
        livroDTO.setAutorId(autorId);

        return ResponseEntity.status(HttpStatus.CREATED).body(livroDTO);
    }

    public static List<LivroDTO> findAll() {
        List<Livro> livros = livroRepository.findAll();
        List<LivroDTO> livrosDTO = livros.stream().map(LivroDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(livrosDTO).getBody();
    }

    public static ResponseEntity<LivroDTO> findById (Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        LivroDTO livroDTO = new LivroDTO(livro);

        return ResponseEntity.ok().body(livroDTO);
    }

    public static ResponseEntity<List<LivroDTO>> findByAutor (String autor) {
        List<Livro> autores = new ArrayList<>();

        livroRepository.findAll().forEach(livro -> {
            if (livro.getAutor().getNome().equals(autor)) {
                autores.add(livro);
            }
        });

        List<LivroDTO> livrosDTO = autores.stream().map(LivroDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<List<LivroDTO>> findByGenero (String genero) {
        List<Livro> generos = new ArrayList<>();

        livroRepository.findAll().forEach(livro -> {
            if (livro.getGenero().equals(genero)) {
                generos.add(livro);
            }
        });

        List<LivroDTO> livrosDTO = generos.stream().map(LivroDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<List<LivroDTO>> findByEditora (String editora) {
        List<Livro> livros = new ArrayList<>();

        livroRepository.findAll().forEach(livro -> {
            if (livro.getEditora().equals(editora)) {
                livros.add(livro);
            }
        });

        List<LivroDTO> livrosDTO = livros.stream().map(LivroDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<LivroDTO> findByTitulo (String titulo) {
        Livro livro = livroRepository.findByTitulo(titulo)
                .orElseThrow(RuntimeException::new);

        LivroDTO livroDTO = new LivroDTO(livro);

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

        List<LivroDTO> livrosDTO = livros.stream().map(LivroDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(livrosDTO);
    }

    public static ResponseEntity<LivroDTO> update (Long id, LivroDTO livroDTO) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        LivroDTO novoLivroDTO = new LivroDTO(livro);
        livro.setId(novoLivroDTO.getId());
        livro.setTitulo(livroDTO.getTitulo());
        livro.setGenero(livroDTO.getGenero());
        livro.setEditora(livroDTO.getEditora());
        livro.setPreco(livroDTO.getPreco());
        livro.setNumeroDePaginas(livroDTO.getNumeroDePaginas());

        livroRepository.save(livro);

        return ResponseEntity.ok().body(livroDTO);
    }

    public static ResponseEntity<Void> delete(Long id) {
        livroRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
