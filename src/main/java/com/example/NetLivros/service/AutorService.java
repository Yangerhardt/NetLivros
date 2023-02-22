package com.example.NetLivros.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.NetLivros.mapper.AutorMapper;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.repository.AutorRepository;

@Service
public class AutorService {

	private final AutorRepository autorRepository;
	private final AutorMapper mapper;

	
	public AutorService(AutorRepository autorRepository, AutorMapper mapper) {
		this.autorRepository = autorRepository;
		this.mapper = mapper;
	}

	public ResponseEntity<AutorDTO> save(AutorDTO autorDTO) {
		Autor autor = mapper.toAutor(autorDTO);
		autorRepository.save(autor);
		autorDTO = mapper.toAutorDTO(autor);
		return ResponseEntity.status(HttpStatus.CREATED).body(autorDTO);
	}

	public ResponseEntity<List<AutorDTO>> findAll() {
		List<Autor> autores = autorRepository.findAll();
		List<AutorDTO> autoresDTO = mapper.toAutorDTOList(autores);

		return ResponseEntity.ok().body(autoresDTO);
	}

	public ResponseEntity<AutorDTO> findById(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(RuntimeException::new);
		AutorDTO autorDTO = mapper.toAutorDTO(autor);
		return ResponseEntity.ok().body(autorDTO);
	}

	public ResponseEntity<AutorDTO> update(Long id, AutorDTO autorDTO) {
		Autor autor = autorRepository.findById(id).orElseThrow(RuntimeException::new);
		autorDTO.setId(id);
		autor = mapper.toAutor(autorDTO);
		autorRepository.save(autor);
		return ResponseEntity.ok().body(autorDTO);
	}

	public ResponseEntity<Void> delete(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(RuntimeException::new);

		autorRepository.delete(autor);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
