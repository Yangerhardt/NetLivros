package com.example.NetLivros.service;

import com.example.NetLivros.model.Autor;
import com.example.NetLivros.model.Livro;
import com.example.NetLivros.model.dto.AutorDTO;
import com.example.NetLivros.model.dto.LivroDTO;
import com.example.NetLivros.repository.AutorRepository;
import com.example.NetLivros.repository.LivroRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LivroServiceTest {

    @Mock
    private AutorRepository autorRepository;
    @Mock
    private LivroRepository livroRepository;
    @Mock
    private AutorDTO autorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LivroService livroService = new LivroService(livroRepository, autorRepository);
        AutorService autorService = new AutorService(autorRepository);
    }

    @Test
    void deveriaRetornarExceptionQuandoNaoEncontrarIdDoUsuario() {
        Mockito.when(livroRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> LivroService.findById(Mockito.any()));
    }

    @Test
    void deveriaCadastrarUmNovoLivroNoBancoDeDados() {
        Livro livro = livro();
        LivroDTO livroDTO = new LivroDTO(livro);
        Autor autor = autor();

        Mockito.when(autorRepository.findById(Mockito.any())).thenReturn(Optional.of(autor));

        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);
        ResponseEntity<LivroDTO> cadastrarLivro = LivroService.save(1L, livroDTO);

        assertNotNull(cadastrarLivro);
        assertEquals(HttpStatus.CREATED, cadastrarLivro.getStatusCode());
        assertEquals(1L, cadastrarLivro.getBody().getAutorId());
        assertEquals("Teste Titulo", cadastrarLivro.getBody().getTitulo());
        assertEquals("Teste Editora", cadastrarLivro.getBody().getEditora());
        assertEquals("Teste Genero", cadastrarLivro.getBody().getGenero());
        assertEquals(10.0, cadastrarLivro.getBody().getPreco());
        assertEquals(1L, cadastrarLivro.getBody().getId());
    }

    @Test
    void deveriaRetornarTodosOsLivrosCadastrados() {
        List<Livro> livros = livros();
        Mockito.when(livroRepository.findAll()).thenReturn(livros);
        List<LivroDTO> todosLivros = LivroService.findAll();

        assertFalse(todosLivros.isEmpty());
        assertEquals(2, todosLivros.size());
        assertEquals("Teste Titulo", todosLivros.get(0).getTitulo());
        assertEquals("Teste Editora", todosLivros.get(0).getEditora());
        assertEquals("Teste Genero", todosLivros.get(0).getGenero());
        assertEquals(10.0, todosLivros.get(0).getPreco());
    }

    @Test
    void deveriaEncontrarUmLivroPeloSeuId() {
        Livro livro = livro();
        Mockito.when(livroRepository.findById(Mockito.any())).thenReturn(Optional.of(livro));
        LivroDTO livroDTO = LivroService.findById(1L).getBody();

        assertNotNull(livroDTO);
        assertEquals(1L, livroDTO.getId());
        assertEquals("Teste Titulo", livroDTO.getTitulo());
        assertEquals("Teste Editora", livroDTO.getEditora());
        assertEquals("Teste Genero", livroDTO.getGenero());
        assertEquals(10.0, livroDTO.getPreco());
    }

    @Test
    void deveriaRetornarUmArrayVazioQuandoNaoEncontrarAutores() {
        List<Livro> livros = livros();
        List<LivroDTO> livrosDTO = LivroService.findByAutor("Any").getBody();

        assert livrosDTO != null;
        assertTrue(livrosDTO.isEmpty());
    }

    @Test
    void deveriaEncontrarLivrosPublicadosPorAutoresEspecificos() {
        List<Livro> livros = livros();

        Mockito.when(livroRepository.findAll()).thenReturn(livros);
        List<LivroDTO> livrosDTO = LivroService.findByAutor("Autor Teste").getBody();

        assert livrosDTO != null;
        assertFalse(livrosDTO.isEmpty());
        assertEquals("Teste Titulo", livrosDTO.get(0).getTitulo());
        assertEquals("Teste Editora", livrosDTO.get(0).getEditora());
        assertEquals("Teste Genero", livrosDTO.get(0).getGenero());
        assertEquals(10.0, livrosDTO.get(0).getPreco());
    }

    @Test
    void deveriaEncontrarLivrosPublicadosPorGeneroEspecifico() {
        List<Livro> livros = livros();

        Mockito.when(livroRepository.findAll()).thenReturn(livros);
        List<LivroDTO> livrosDTO = LivroService.findByGenero("Teste Genero").getBody();

        assert livrosDTO != null;
        assertFalse(livrosDTO.isEmpty());
        assertEquals("Teste Titulo", livrosDTO.get(0).getTitulo());
        assertEquals("Teste Editora", livrosDTO.get(0).getEditora());
        assertEquals(10.0, livrosDTO.get(0).getPreco());
    }

    @Test
    void deveriaEncontrarLivrosPublicadosPorEditoraEspecifica() {
        List<Livro> livros = livros();

        Mockito.when(livroRepository.findAll()).thenReturn(livros);
        List<LivroDTO> livrosDTO = LivroService.findByEditora("Teste Editora").getBody();

        assert livrosDTO != null;
        assertFalse(livrosDTO.isEmpty());
        assertEquals("Teste Titulo", livrosDTO.get(0).getTitulo());
        assertEquals("Teste Genero", livrosDTO.get(0).getGenero());
        assertEquals(10.0, livrosDTO.get(0).getPreco());
    }

    @Test
    void deveriaEncontrarLivroPeloTitulo() {
        Livro livro = livro();

        Mockito.when(livroRepository.findByTitulo(Mockito.any())).thenReturn(Optional.of(livro));
        LivroDTO livroDTO = LivroService.findByTitulo("Teste Titulo").getBody();

        assert livroDTO != null;
        assertEquals("Teste Editora", livroDTO.getEditora());
        assertEquals("Teste Genero", livroDTO.getGenero());
        assertEquals(10.0, livroDTO.getPreco());
    }

    @Test
    void deveriaRetornarExceptionQuandoOTituloNaoExistir() {
        Mockito.when(livroRepository.findByTitulo(Mockito.any())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> LivroService.findByTitulo("Teste Titulo"));
    }

    @Test
    void deveriaRetornarOsLivrosAbaixoDoPrecoInformado() {
        List<Livro> livros = livros();

        Mockito.when(livroRepository.findAll()).thenReturn(livros);
        List<LivroDTO> livrosDTO = LivroService.findByPreco(20.0).getBody();

        assert livrosDTO != null;
        assertFalse(livrosDTO.isEmpty());
        assertEquals("Teste Titulo", livrosDTO.get(0).getTitulo());
        assertEquals("Teste Genero", livrosDTO.get(0).getGenero());
        assertEquals("Teste Editora", livrosDTO.get(0).getEditora());
    }

    @Test
    void deveriaRetornarVazioSeNaoHouveremLivrosAbaixoDoPrecoInformado() {
        List<Livro> livros = livros();
        Double preco = livros.get(0).getPreco();
        Double menor = preco - 1;

        Mockito.when(livroRepository.findAll()).thenReturn(livros);
        List<LivroDTO> livrosDTO = LivroService.findByPreco(menor).getBody();

        assert livrosDTO != null;
        assertTrue(livrosDTO.isEmpty());
    }

    @Test
    void deveriaRetornarUmArrayVazioNoCasoDeNaoSerPassadoParametroDePreco() {
        List<LivroDTO> livrosDTO = LivroService.findByPreco(null).getBody();

        assert livrosDTO != null;
        assertTrue(livrosDTO.isEmpty());
    }

    @Test
    void deveriaAtualizarUmLivroNoBancoDeDados() {
        Livro livro = livro();
        Livro novoLivro = new Livro(
                "Novo Teste", 140, 15.5, "Novo Genero", "Nova Editora", autor());
        LivroDTO novoDTO = new LivroDTO(novoLivro);

        Mockito.when(livroRepository.findById(Mockito.any())).thenReturn(Optional.of(livro));
        Mockito.when(livroRepository.save(Mockito.any())).thenReturn(livro);
        ResponseEntity<LivroDTO> atualizarLivro = LivroService.update(1L, novoDTO);

        assertNotNull(atualizarLivro);
        assertEquals(HttpStatus.OK, atualizarLivro.getStatusCode());
        assertEquals(1L, atualizarLivro.getBody().getAutorId());
        assertEquals("Novo Teste", atualizarLivro.getBody().getTitulo());
        assertEquals("Nova Editora", atualizarLivro.getBody().getEditora());
        assertEquals("Novo Genero", atualizarLivro.getBody().getGenero());
        assertEquals(15.5, atualizarLivro.getBody().getPreco());
        assertEquals(140, atualizarLivro.getBody().getNumeroDePaginas());
    }

    @Test
    void deveriaRemoverUmLivroDoBancoDeDados() {
        ResponseEntity<Void> delete = LivroService.delete(1L);

        Mockito.verify(livroRepository, Mockito.times(1)).deleteById(1L);
        assertNotNull(delete);
        assertEquals(HttpStatus.NO_CONTENT, delete.getStatusCode());
    }

    private Livro livro() {
        Livro livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Teste Titulo");
        livro.setAutor(new Autor("Autor Teste"));
        livro.setGenero("Teste Genero");
        livro.setEditora("Teste Editora");
        livro.setPreco(10.0);
        return livro;
    }

    private List<Livro> livros() {
        List<Livro> livros = new ArrayList<>();
        livros.add(livro());
        livros.add(livro());
        return livros;
    }

    private @NotNull Autor autor() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome("Test");
        return autor;
    }
}