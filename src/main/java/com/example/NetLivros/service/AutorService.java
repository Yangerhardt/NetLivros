package com.example.NetLivros.service;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.repository.AutorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    public static AutorRepository autorRepository;

    public AutorService(AutorRepository repository) {
        AutorService.autorRepository = repository;
    }

    public static ResponseEntity<AutorDTO> save (@Valid AutorDTO autorDTO) {
        Autor autor = new Autor();
        BeanUtils.copyProperties(autorDTO, autor);
        autorRepository.save(autor);
        BeanUtils.copyProperties(autor, autorDTO);

        return ResponseEntity.ok().body(autorDTO);
    }

    public static List<AutorDTO> findAll() {
        List<Autor> autores = autorRepository.findAll();
        List<AutorDTO> autoresDTO = autores.stream().map(AutorDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok().body(autoresDTO).getBody();
    }

    public static ResponseEntity<AutorDTO> findById(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        AutorDTO autorDTO = new AutorDTO(autor);
        return ResponseEntity.ok().body(autorDTO);

    }

    public static ResponseEntity<AutorDTO> update(Long id, Autor autor) {
        Autor aut = autorRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        autor.setId(id);
        autorRepository.save(aut);

        AutorDTO autorDTO = new AutorDTO(autor);
        return ResponseEntity.ok().body(autorDTO);
    }

    public static ResponseEntity<Autor> delete(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        autorRepository.delete(autor);
        return ResponseEntity.ok().body(autor);
    }
}
