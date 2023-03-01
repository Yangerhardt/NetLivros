package com.example.NetLivros.service;

import com.example.NetLivros.exceptions.RecursoNaoEncontradoException;
import com.example.NetLivros.mapper.AutorMapper;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.repository.AutorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    public static AutorRepository autorRepository;
    public static AutorMapper mapper = new AutorMapper();

    public AutorService(AutorRepository repository, AutorMapper autorMapper) {
        AutorService.autorRepository = repository;
        AutorMapper.autorMapper = autorMapper;
    }

    public static ResponseEntity<AutorDTO> save (@Valid AutorDTO autorDTO) {
        Autor autor = mapper.toAutor(autorDTO);
        autorRepository.save(autor);
        autorDTO = mapper.toAutorDTO(autor);

        return ResponseEntity.status(HttpStatus.CREATED).body(autorDTO);
    }

    public static List<AutorDTO> findAll() {
        List<Autor> autores = autorRepository.findAll();
        List<AutorDTO> autoresDTO = mapper.toAutorDTOList(autores);

        return ResponseEntity.ok().body(autoresDTO).getBody();
    }

    public static ResponseEntity<AutorDTO> findById(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado"));

        AutorDTO autorDTO = mapper.toAutorDTO(autor);
        return ResponseEntity.ok().body(autorDTO);
    }

    public static ResponseEntity<AutorDTO> update(Long id, Autor autor) {
        Autor aut = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado"));
        autor.setId(id);
        autorRepository.save(aut);

        AutorDTO autorDTO = mapper.toAutorDTO(autor);
        return ResponseEntity.ok().body(autorDTO);
    }

    public static ResponseEntity<Autor> delete(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Autor não encontrado"));

        autorRepository.delete(autor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
