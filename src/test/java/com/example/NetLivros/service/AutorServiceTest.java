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
    @Mock
    private AutorDTO dto;
    @Mock
    private Autor autor;

    @Captor
    private ArgumentCaptor<Autor> autorCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.service = new AutorService(repository);
    }

    @Test
    void deveriaSalvarUmNovoAutorNoBancoDeDados() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(autor);
        ResponseEntity<AutorDTO> autor = service.save(dto);
        assertNotNull(autor);
        assertEquals(HttpStatus.CREATED, autor.getStatusCode());
    }

    @Test
    void deveriaRetornarTodosOsAutoresCadastrados() {
        List<Autor> autores = autores();
        Mockito.when(repository.findAll()).thenReturn(autores);
        List<AutorDTO> all = service.findAll();

        assertFalse(all.isEmpty());
    }
    @Test
    void deveriaRetornarVazioQuandoNaoHaAutoresCadastrados() {
        List<AutorDTO> all = service.findAll();
        assertTrue(all.isEmpty());
    }

//    @Test
//    void findById() {
//        Autor autor = autor();
//
//        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(autor));
//        ResponseEntity<AutorDTO> autorById = service.findById(Mockito.any());
//        assertNotNull(autorById);
//        assertEquals(autor, autorById);
//    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private List<Autor> autores() {
       List<Autor> autores = new ArrayList<>();
       autores.add(autor);
       return autores;
    }

    private Autor autor() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Test");
        return autor;
    }
}