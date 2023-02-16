package com.example.NetLivros.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.repository.AutorRepository;

@Service
public class AutorService {

	public static AutorRepository autorRepository;

	public AutorService(AutorRepository repository) {
		AutorService.autorRepository = repository;
	}

	public static ResponseEntity<AutorDTO> save(AutorDTO autorDTO) {
		Autor autor = new Autor();
		BeanUtils.copyProperties(autorDTO, autor);
		autorRepository.save(autor);
		BeanUtils.copyProperties(autor, autorDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(autorDTO);
	}

	public static ResponseEntity<List<AutorDTO>> findAll() {
		List<Autor> autores = autorRepository.findAll();
		List<AutorDTO> autoresDTO = autores.stream().map(autor -> new AutorDTO(autor)).toList();

		return ResponseEntity.ok().body(autoresDTO);
	}

	public static ResponseEntity<AutorDTO> findById(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(RuntimeException::new);
		AutorDTO autorDTO = new AutorDTO(autor);
		return ResponseEntity.ok().body(autorDTO);
	}

	public static ResponseEntity<AutorDTO> update(Long id, AutorDTO autorDTO) {
		Autor autor = autorRepository.findById(id).orElseThrow(RuntimeException::new);
		autor.setId(id);
		autorRepository.save(autor);
		autorDTO = new AutorDTO(autor);
		return ResponseEntity.ok().body(autorDTO);
	}

	public static ResponseEntity<Void> delete(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(RuntimeException::new);

		autorRepository.delete(autor);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
