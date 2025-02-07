package com.biblioteca.gestor_biblioteca.modules.prestamo.application.services;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.repository.LibroRepository;
import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.model.Prestamo;
import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.repository.PrestamoRepository;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.exceptions.PrestamoException;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class PrestamoServiceTest {


    @InjectMocks
    private PrestamoService prestamoService;

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    private Prestamo prestamoEjemplo;
    private Libro libroEjemplo;
    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        libroEjemplo = new Libro(1L, "1984", "George Orwell", "9780451524935", LocalDate.of(1949, 6, 8));
        usuarioEjemplo = new Usuario(1L, "Juan Pérez", "juan.perez@example.com", "123456789", LocalDate.of(2020, 1, 15));
        prestamoEjemplo = new Prestamo(1L, libroEjemplo, usuarioEjemplo, LocalDate.now(), LocalDate.now().plusDays(14));
    }
    @Test
    void obtenerPrestamos_DeberiaRetornarListaDePrestamos() {
        when(prestamoRepository.obtenerPrestamos()).thenReturn(List.of(prestamoEjemplo));

        List<Prestamo> resultado = prestamoService.obtenerPrestamos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(libroEjemplo, resultado.get(0).getLibro());
    }

    @Test
    void obtenerPrestamoPorId_DeberiaRetornarPrestamoSiExiste() {
        when(prestamoRepository.obtenerPrestamoPorId(1L)).thenReturn(Optional.of(prestamoEjemplo));

        Prestamo resultado = prestamoService.obtenerPrestamoPorId(1L);

        assertNotNull(resultado);
        assertEquals(libroEjemplo, resultado.getLibro());
    }

    @Test
    void obtenerPrestamoPorId_DeberiaLanzarExcepcionSiNoExiste() {
        when(prestamoRepository.obtenerPrestamoPorId(99L)).thenReturn(Optional.empty());

        PrestamoException exception = assertThrows(PrestamoException.class,
                () -> prestamoService.obtenerPrestamoPorId(99L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void guardarPrestamo_DeberiaGuardarPrestamoSiEsValido() {
        when(libroRepository.obtenerLibroPorId(any())).thenReturn(Optional.of(libroEjemplo));
        when(usuarioRepository.obtenerUsuarioPorId(any())).thenReturn(Optional.of(usuarioEjemplo));
        when(prestamoRepository.guardarActualizarPrestamo(any())).thenReturn(prestamoEjemplo);

        Prestamo resultado = prestamoService.guardarPrestamo(prestamoEjemplo);

        assertNotNull(resultado);
        assertEquals(libroEjemplo, resultado.getLibro());
    }

    @Test
    void guardarPrestamo_DeberiaLanzarExcepcionSiLibroNoExiste() {
        when(libroRepository.obtenerLibroPorId(any())).thenReturn(Optional.empty());
        when(usuarioRepository.obtenerUsuarioPorId(any())).thenReturn(Optional.of(usuarioEjemplo));

        PrestamoException exception = assertThrows(PrestamoException.class,
                () -> prestamoService.guardarPrestamo(prestamoEjemplo));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void guardarPrestamo_DeberiaLanzarExcepcionSiUsuarioNoExiste() {
        when(libroRepository.obtenerLibroPorId(any())).thenReturn(Optional.of(libroEjemplo));
        when(usuarioRepository.obtenerUsuarioPorId(any())).thenReturn(Optional.empty());

        PrestamoException exception = assertThrows(PrestamoException.class,
                () -> prestamoService.guardarPrestamo(prestamoEjemplo));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void guardarPrestamo_DeberiaLanzarExcepcionSiLibroEstaPrestado() {
        when(prestamoRepository.obtenerPrestamosPorLibroId(libroEjemplo.getId()))
                .thenReturn(List.of(prestamoEjemplo));
        when(usuarioRepository.obtenerUsuarioPorId(any())).thenReturn(Optional.of(usuarioEjemplo));

        PrestamoException exception = assertThrows(PrestamoException.class,
                () -> prestamoService.guardarPrestamo(prestamoEjemplo));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void actualizarPrestamo_DeberiaActualizarPrestamoSiExiste() {
        when(prestamoRepository.obtenerPrestamoPorId(1L)).thenReturn(Optional.of(prestamoEjemplo));
        when(libroRepository.obtenerLibroPorId(any())).thenReturn(Optional.of(libroEjemplo));
        when(usuarioRepository.obtenerUsuarioPorId(any())).thenReturn(Optional.of(usuarioEjemplo));
        when(prestamoRepository.guardarActualizarPrestamo(any())).thenReturn(prestamoEjemplo);

        Prestamo resultado = prestamoService.actualizarPrestamo(1L, prestamoEjemplo);

        assertNotNull(resultado);
        assertEquals(libroEjemplo, resultado.getLibro());
    }

    @Test
    void actualizarPrestamo_DeberiaLanzarExcepcionSiPrestamoNoExiste() {
        when(prestamoRepository.obtenerPrestamoPorId(99L)).thenReturn(Optional.empty());

        PrestamoException exception = assertThrows(PrestamoException.class,
                () -> prestamoService.actualizarPrestamo(99L, prestamoEjemplo));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void actualizarPrestamo_DeberiaLanzarExcepcionSiLibroEstaPrestado() {
        when(prestamoRepository.obtenerPrestamoPorId(1L)).thenReturn(Optional.of(prestamoEjemplo));
        when(libroRepository.obtenerLibroPorId(any())).thenReturn(Optional.of(libroEjemplo));
        when(usuarioRepository.obtenerUsuarioPorId(any())).thenReturn(Optional.of(usuarioEjemplo));
        when(prestamoRepository.obtenerPrestamosPorLibroId(libroEjemplo.getId()))
                .thenReturn(List.of(prestamoEjemplo));

        PrestamoException exception = assertThrows(PrestamoException.class,
                () -> prestamoService.actualizarPrestamo(1L, prestamoEjemplo));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void borrarPrestamoPorId_DeberiaEliminarPrestamoSiExiste() {
        when(prestamoRepository.obtenerPrestamoPorId(1L)).thenReturn(Optional.of(prestamoEjemplo));
        doNothing().when(prestamoRepository).borrarPrestamoPorId(1L);

        assertDoesNotThrow(() -> prestamoService.borrarPrestamoPorId(1L));
    }

    @Test
    void borrarPrestamoPorId_DeberiaLanzarExcepcionSiPrestamoNoExiste() {
        when(prestamoRepository.obtenerPrestamoPorId(99L)).thenReturn(Optional.empty());

        PrestamoException exception = assertThrows(PrestamoException.class,
                () -> prestamoService.borrarPrestamoPorId(99L));
        assertEquals(404, exception.getErrorCode());
    }
}