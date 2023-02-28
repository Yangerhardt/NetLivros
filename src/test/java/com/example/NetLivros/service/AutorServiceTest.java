package com.example.NetLivros.service;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AutorServiceTest {

    private AutorService service;

    @Mock
    private AutorRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.service = new AutorService(repository);
    }

    @Test
    void deveriaRetornarUmaExcecaoQuandoOIdNaoExistir() {
        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.findById(Mockito.any()));
    }

    @Test
    void deveriaCadastrarUmNovoAutorNoBancoDeDados() {
        Autor autor = autor();
        AutorDTO autorDTO = new AutorDTO(autor);

        Mockito.when(repository.save(Mockito.any())).thenReturn(autor);
        ResponseEntity<AutorDTO> dto = service.save(autorDTO);
        assertNotNull(autor);
        assertEquals(HttpStatus.CREATED, dto.getStatusCode());
        assertEquals("Test", dto.getBody().getNome());
    }

    @Test
    void deveriaRetornarTodosOsAutoresCadastrados() {
        List<Autor> autores = autores();
        Mockito.when(repository.findAll()).thenReturn(autores);
        List<AutorDTO> all = service.findAll();

        assertFalse(all.isEmpty());
        assertEquals(1, all.size());
        assertEquals("Test", all.get(0).getNome());

    }
    @Test
    void deveriaRetornarVazioQuandoNaoHaAutoresCadastrados() {
        List<AutorDTO> all = service.findAll();
        assertTrue(all.isEmpty());
    }

    @Test
    void deveriaRetornarUmAutorEspecificoQueFoiPassadoComoParametroDeId() {
        Autor autor = autor();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(autor));
        ResponseEntity<AutorDTO> autorById = service.findById(1L);
        assertNotNull(autorById);
        assertEquals(HttpStatus.OK, autorById.getStatusCode());
        assertEquals("Test", autorById.getBody().getNome());
    }

    @Test
    void deveriaAtualizarUmAutorNoBancoDeDados() {
        Autor autor = autor();
        Autor novoAutor = new Autor("Novo");

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(autor));
        ResponseEntity<AutorDTO> update = AutorService.update(1L, novoAutor);
        assertNotNull(update);
        assertEquals(HttpStatus.OK, update.getStatusCode());
        assertEquals("Novo", update.getBody().getNome());
    }

    @Test
    void deveriaDeletarUmAutor() {
        Autor autor = autor();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(autor));
        ResponseEntity<Autor> delete = service.delete(1L);
        assertNotNull(delete);
        assertEquals(HttpStatus.NO_CONTENT, delete.getStatusCode());
    }

    private List<Autor> autores() {
       List<Autor> autores = new ArrayList<>();
       autores.add(autor());
       return autores;
    }

    private Autor autor() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Test");
        return autor;
    }
}