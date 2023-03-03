package com.example.NetLivros.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.NetLivros.exception.ResourceNotFoundException;
import com.example.NetLivros.mapper.AutorMapper;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.repository.AutorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class AutorService {

	private final AutorRepository autorRepository;
	private final AutorMapper mapper;
	
	public AutorDTO save(AutorDTO autorDTO) {
		Autor autor = mapper.toAutor(autorDTO);
		autorRepository.save(autor);
		autorDTO = mapper.toAutorDTO(autor);
		log.info("Salvando Autor no Banco de Dados");
		return autorDTO;
	}

	public List<AutorDTO> findAll() {
		List<Autor> autores = autorRepository.findAll();
		List<AutorDTO> autoresDTO = mapper.toAutorDTOList(autores);

		log.info("Lendo Autores do Banco de Dados");
		return autoresDTO;
	}

	public AutorDTO findById(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado!"));
		AutorDTO autorDTO = mapper.toAutorDTO(autor);

		log.info("Buscando Autor Por ID no Banco de Dados");
		return autorDTO;
	}

	public AutorDTO update(Long id, AutorDTO autorDTO) {
		Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado!"));
		autorDTO.setId(id);
		autor = mapper.toAutor(autorDTO);
		autorRepository.save(autor);

		log.info("Atualizando Autor Por ID no Banco de Dados");
		return autorDTO;
	}

	public void deleteById(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado!"));

		log.info("Deletando Autor Por ID do Banco de Dados");
		autorRepository.delete(autor);
	}
}
