package com.example.NetLivros.service;

import static com.example.NetLivros.mock.MocksAutor.AUTOR_1;
import static com.example.NetLivros.mock.MocksLivro.INVALID_LIVRO;
import static com.example.NetLivros.mock.MocksLivro.INVALID_LIVRO_DTO;
import static com.example.NetLivros.mock.MocksLivro.LIVROS;
import static com.example.NetLivros.mock.MocksLivro.LIVROS_DTO;
import static com.example.NetLivros.mock.MocksLivro.LIVRO_1;
import static com.example.NetLivros.mock.MocksLivro.LIVRO_DTO_1;
import static com.example.NetLivros.mock.MocksLivro.LIVRO_DTO_2;
import static com.example.NetLivros.mock.MocksLivro.LIVRO_DTO_3;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.NetLivros.exception.ResourceNotFoundException;
import com.example.NetLivros.exception.ResourceNotValidException;
import com.example.NetLivros.mapper.LivroMapper;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;

@ExtendWith(MockitoExtension.class)
class LivroServiceTest {

	@InjectMocks
	private LivroService service;
	@Mock
	private LivroRepository repository;
	@Mock
	private AutorRepository autorRepository;
	@Mock
	private LivroMapper mapper;

	@DisplayName("Deveria salvar livro sem lançar exceção")
	@Test
	void testSave() {
		when(autorRepository.findById(anyLong())).thenReturn(Optional.of(AUTOR_1));
		when(repository.save(any(Livro.class))).thenReturn(LIVRO_1);
		when(mapper.toLivro(any())).thenReturn(LIVRO_1);
		when(mapper.toLivroDTO(any())).thenReturn(LIVRO_DTO_1);
		
		
		LivroDTO savedLivro = service.save(1L, LIVRO_DTO_1);
		
		assertThat(savedLivro).isEqualTo(LIVRO_DTO_1);
		
	}

	@DisplayName("Deveria lançar exceção ao tentar salvar livro invalido ")
	@Test
	void testSave_InvalidLivro() {
		when(autorRepository.findById(anyLong())).thenReturn(Optional.of(AUTOR_1));
		when(repository.save(INVALID_LIVRO)).thenThrow(ResourceNotValidException.class);
		lenient().when(mapper.toLivro(any())).thenReturn(INVALID_LIVRO);
		lenient().when(mapper.toLivroDTO(any())).thenReturn(INVALID_LIVRO_DTO);

		assertThatThrownBy(() -> service.save(1L, INVALID_LIVRO_DTO)).isInstanceOf(ResourceNotValidException.class);

	}

	@DisplayName("Deveria listar livros sem lançar exceção")
	@Test
	void testFindAll() {
		lenient().when(repository.findAll()).thenReturn(LIVROS);
		when(mapper.toLivroDTOList(any())).thenReturn(LIVROS_DTO);

		List<LivroDTO> livrosList = service.findAll(null, null, null, null, null);

		assertThat(livrosList).isEqualTo(LIVROS_DTO);
		assertThat(livrosList).hasSize(3);
		assertThat(livrosList.get(0)).isEqualTo(LIVRO_DTO_1);
		assertThat(livrosList.get(1)).isEqualTo(LIVRO_DTO_2);
		assertThat(livrosList.get(2)).isEqualTo(LIVRO_DTO_3);

	}

	@DisplayName("Deveria buscar um livro sem lançar exceção")
	@Test
	void testFindById() {
		when(repository.findById(anyLong())).thenReturn(Optional.of(LIVRO_1));
		when(mapper.toLivroDTO(any())).thenReturn(LIVRO_DTO_1);
		
		LivroDTO livroDTO = service.findById(1L);
		
		assertThat(livroDTO).isEqualTo(LIVRO_DTO_1);
		
	}
	@DisplayName("Deveria lançar exceção ao buscar um livro inexistente")
	@Test
	void testFindById_LivroNotFound() {
		when(repository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThatThrownBy(() -> service.findById(1L)).isInstanceOf(ResourceNotFoundException.class);
		
	}

	@DisplayName("Deveria atualizar autor sem lançar exceção")
	@Test
	void testUpdate() {
		when(repository.findById(anyLong())).thenReturn(Optional.of(LIVRO_1));
		when(mapper.toLivroDTO(any())).thenReturn(LIVRO_DTO_1);
		when(mapper.toLivro(any())).thenReturn(LIVRO_1);
		
		LIVRO_DTO_1.setTitulo("Titulo Updated");
		LIVRO_DTO_1.setEditora("Editora Updated");
		LIVRO_DTO_1.setGenero("Genero Updated");
		LIVRO_DTO_1.setNumeroDePaginas(123);
		LIVRO_DTO_1.setPreco(321.0);
		LivroDTO updatedLivro = service.update(1L, LIVRO_DTO_1);
		
		assertThat(updatedLivro.getTitulo()).isEqualTo(LIVRO_DTO_1.getTitulo());
		assertThat(updatedLivro.getEditora()).isEqualTo(LIVRO_DTO_1.getEditora());
		assertThat(updatedLivro.getGenero()).isEqualTo(LIVRO_DTO_1.getGenero());
		assertThat(updatedLivro.getNumeroDePaginas()).isEqualTo(LIVRO_DTO_1.getNumeroDePaginas());
		assertThat(updatedLivro.getPreco()).isEqualTo(LIVRO_DTO_1.getPreco());
		
		
	}

	@DisplayName("Deveria deletar livro sem lançar exceção")
	@Test
	void testDeleteById() {
		when(repository.findById(anyLong())).thenReturn(Optional.of(LIVRO_1));

		assertDoesNotThrow(() -> service.deleteById(anyLong()));

	}
	
	@DisplayName("Deveria lançar exceção tentar deletar livro inexistente")
	@Test
	void testDeleteById_LivroNotFound() {
		when(repository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThatThrownBy(() -> service.deleteById(1L)).isInstanceOf(ResourceNotFoundException.class);
		
	}
	
	
	

}
