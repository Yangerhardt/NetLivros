package com.example.NetLivros.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.NetLivros.exception.ResourceNotFoundException;
import com.example.NetLivros.mapper.AutorMapper;
import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.repository.AutorRepository;

@Service
public class AutorService {

	private final AutorRepository autorRepository;
	private final AutorMapper mapper;
	
	private Logger logger = LoggerFactory.getLogger(AutorService.class);

	
	public AutorService(AutorRepository autorRepository, AutorMapper mapper) {
		this.autorRepository = autorRepository;
		this.mapper = mapper;
	}

	public AutorDTO save(AutorDTO autorDTO) {
		Autor autor = mapper.toAutor(autorDTO);
		autorRepository.save(autor);
		autorDTO = mapper.toAutorDTO(autor);
		logger.info("Salvando Autor no Banco de Dados");
		return autorDTO;
	}

	public List<AutorDTO> findAll() {
		List<Autor> autores = autorRepository.findAll();
		List<AutorDTO> autoresDTO = mapper.toAutorDTOList(autores);

		logger.info("Lendo Autores do Banco de Dados");
		return autoresDTO;
	}

	public AutorDTO findById(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado!"));
		AutorDTO autorDTO = mapper.toAutorDTO(autor);

		logger.info("Buscando Autor Por ID no Banco de Dados");
		return autorDTO;
	}

	public AutorDTO update(Long id, AutorDTO autorDTO) {
		Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado!"));
		autorDTO.setId(id);
		autor = mapper.toAutor(autorDTO);
		autorRepository.save(autor);

		logger.info("Atualizando Autor Por ID no Banco de Dados");
		return autorDTO;
	}

	public void deleteById(Long id) {
		Autor autor = autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado!"));

		logger.info("Deletando Autor Por ID do Banco de Dados");
		autorRepository.delete(autor);
	}
}
