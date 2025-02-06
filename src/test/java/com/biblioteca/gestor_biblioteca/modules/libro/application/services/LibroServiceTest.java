package com.biblioteca.gestor_biblioteca.modules.libro.application.services;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.repository.LibroRepository;
import com.biblioteca.gestor_biblioteca.modules.libro.infrastructure.rest.exceptions.LibroException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    private Libro libroEjemplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        libroEjemplo = new Libro(1L, "El Principito", "Antoine de Saint-Exupéry", "123456789",
                LocalDate.of(1943, 4, 6));
    }

    @Test
    void obtenerLibros_DeberiaRetornarListaDeLibros() {
        when(libroRepository.obtenerLibros()).thenReturn(List.of(libroEjemplo));

        List<Libro> resultado = libroService.obtenerLibros();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("El Principito", resultado.get(0).getTitulo());
    }

    @Test
    void obtenerLibroPorId_DeberiaRetornarLibroSiExiste() {
        when(libroRepository.obtenerLibroPorId(1L)).thenReturn(Optional.of(libroEjemplo));

        Libro resultado = libroService.obtenerLibroPorId(1L);

        assertNotNull(resultado);
        assertEquals("El Principito", resultado.getTitulo());
    }

    @Test
    void obtenerLibroPorId_DeberiaLanzarExcepcionSiNoExiste() {
        when(libroRepository.obtenerLibroPorId(99L)).thenReturn(Optional.empty());

        LibroException exception = assertThrows(LibroException.class,
                () -> libroService.obtenerLibroPorId(99L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void guardarLibro_DeberiaGuardarLibroSiEsValido() {
        when(libroRepository.guardarActualizarLibro(libroEjemplo)).thenReturn(libroEjemplo);

        Libro resultado = libroService.guardarLibro(libroEjemplo);

        assertNotNull(resultado);
        assertEquals("El Principito", resultado.getTitulo());
    }

    @Test
    void guardarLibro_DeberiaLanzarExcepcionSiTituloEsVacio() {
        Libro libroInvalido = new Libro(2L, "", "Autor", "987654321", LocalDate.now());

        LibroException exception = assertThrows(LibroException.class,
                () -> libroService.guardarLibro(libroInvalido));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void actualizarLibro_DeberiaActualizarLibroSiExiste() {
        when(libroRepository.obtenerLibroPorId(1L)).thenReturn(Optional.of(libroEjemplo));
        when(libroRepository.guardarActualizarLibro(any())).thenReturn(libroEjemplo);

        Libro resultado = libroService.actualizarLibro(1L, libroEjemplo);

        assertNotNull(resultado);
        assertEquals("El Principito", resultado.getTitulo());
    }

    @Test
    void actualizarLibro_DeberiaLanzarExcepcionSiLibroNoExiste() {
        when(libroRepository.obtenerLibroPorId(99L)).thenReturn(Optional.empty());

        LibroException exception = assertThrows(LibroException.class,
                () -> libroService.actualizarLibro(99L, libroEjemplo));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void actualizarParcialmenteLibro_DeberiaActualizarCamposEspecificos() {
        when(libroRepository.obtenerLibroPorId(1l)).thenReturn(Optional.of(libroEjemplo));
        when(libroRepository.guardarActualizarLibro(any())).thenAnswer(
                invocation -> invocation.getArgument(0));

        Map<String, String> campoParaActualizar = Map.of("titulo", "Libro test");

        Libro resultado = libroService.actualizarParcialmenteLibro(1L, campoParaActualizar);

        assertEquals("Libro test", resultado.getTitulo());
        assertEquals("Antoine de Saint-Exupéry", resultado.getAutor());

    }

    @Test
    void actualizarParcialmenteLibro_DeberiaLanzarExcepcionSiLibroNoExiste(){
        when(libroRepository.obtenerLibroPorId(99L)).thenReturn(Optional.empty());

        Map<String, String> campoParaActualizar = Map.of("titulo", "Libro test");


        LibroException exception = assertThrows(LibroException.class,
                () -> libroService.actualizarParcialmenteLibro(99L, campoParaActualizar));
        assertEquals(404, exception.getErrorCode());

    }

    @Test
    void actualizarParcialmenteLibro_DeberiaLanzarExcepcionSiCampoVacio(){
        when(libroRepository.obtenerLibroPorId(1L)).thenReturn(Optional.of(libroEjemplo));

        Map<String, String> campoParaActualizar = Map.of("titulo", "");

        LibroException exception = assertThrows(LibroException.class,
                () -> libroService.actualizarParcialmenteLibro(1L, campoParaActualizar));
        assertEquals(400, exception.getErrorCode());

    }

    @Test
    void actualizarParcialmenteLibro_DeberiaLanzarExcepcionSiCampoNoValido(){
        when(libroRepository.obtenerLibroPorId(1L)).thenReturn(Optional.of(libroEjemplo));

        Map<String, String> campoParaActualizar = Map.of("editorial", "Editorial Planeta");

        LibroException exception = assertThrows(LibroException.class,
                () -> libroService.actualizarParcialmenteLibro(1L, campoParaActualizar));
        assertEquals(400, exception.getErrorCode());

    }


    @Test
    void borrarLibroPorId_DeberiaEliminarLibroSiExiste() {
        when(libroRepository.obtenerLibroPorId(1L)).thenReturn(Optional.of(libroEjemplo));
        doNothing().when(libroRepository).borrarLibroPorId(1L);

        assertDoesNotThrow(() -> libroService.borrarLibroPorId(1L));
    }

    @Test
    void borrarLibroPorId_DeberiaLanzarExcepcionSiLibroNoExiste() {
        when(libroRepository.obtenerLibroPorId(99L)).thenReturn(Optional.empty());

        LibroException exception = assertThrows(LibroException.class,
                () -> libroService.borrarLibroPorId(99L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void validarLibroAGuardar_DeberiaNoLanzarExcepcionSiLibroEsValido() {
        assertDoesNotThrow(() -> libroService.validarLibroAGuardar(libroEjemplo));
    }

    @Test
    void validarLibroAGuardar_DeberiaLanzarExcepcionSiTituloEsVacio() {
        Libro libroInvalido = new Libro(2L, "", "Autor", "987654321", LocalDate.now());

        LibroException exception = assertThrows(LibroException.class, () -> libroService.validarLibroAGuardar(libroInvalido));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void validarLibroAGuardar_DeberiaLanzarExcepcionSiAutorEsVacio() {
        Libro libroInvalido = new Libro(2L, "Libro de prueba", "", "987654321", LocalDate.now());

        LibroException exception = assertThrows(LibroException.class, () -> libroService.validarLibroAGuardar(libroInvalido));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void validarLibroAGuardar_DeberiaLanzarExcepcionSiIsbnEsVacio() {
        Libro libroInvalido = new Libro(2L, "Libro de prueba", "Autor", "", LocalDate.now());

        LibroException exception = assertThrows(LibroException.class, () -> libroService.validarLibroAGuardar(libroInvalido));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void validarLibroAGuardar_DeberiaLanzarExcepcionSiFechaPublicacionEsNula() {
        Libro libroInvalido = new Libro(2L, "Libro de prueba", "Autor", "123456789", null);

        LibroException exception = assertThrows(LibroException.class, () -> libroService.validarLibroAGuardar(libroInvalido));
        assertEquals(400, exception.getErrorCode());
    }
}