package com.biblioteca.gestor_biblioteca.modules.usuario.application.services;

import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.repository.PrestamoRepository;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.repository.UsuarioRepository;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.exceptions.UsuarioException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PrestamoRepository prestamoRepository;


    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioEjemplo = new Usuario(1L, "Jorge", "jorge@gmail.com", "123456789", LocalDate.of(2025, 2, 6));
    }

    @Test
    void obtenerUsuarios_DeberiaRetornarListaDeUsuarios() {
        when(usuarioRepository.obtenerUsuarios()).thenReturn(List.of(usuarioEjemplo));

        List<Usuario> resultado = usuarioService.obtenerUsuarios();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Jorge", resultado.get(0).getNombre());
    }

    @Test
    void obtenerUsuarioPorId_DeberiaRetornarUsuarioSiExiste() {
        when(usuarioRepository.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuarioEjemplo));

        Usuario resultado = usuarioService.obtenerUsuarioPorId(1L);

        assertNotNull(resultado);
        assertEquals("Jorge", resultado.getNombre());
    }

    @Test
    void obtenerUsuarioPorId_DeberiaLanzarExcepcionSiNoExiste() {
        when(usuarioRepository.obtenerUsuarioPorId(99L)).thenReturn(Optional.empty());

        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.obtenerUsuarioPorId(99L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void guardarUsuario_DeberiaGuardarUsuarioSiEsValido() {
        when(usuarioRepository.guardarActualizarUsuario(usuarioEjemplo)).thenReturn(usuarioEjemplo);

        Usuario resultado = usuarioService.guardarUsuario(usuarioEjemplo);

        assertNotNull(resultado);
        assertEquals("Jorge", resultado.getNombre());
    }

    @Test
    void guardarUsuario_DeberiaLanzarExcepcionSiNombreEsVacio() {
        Usuario usuarioInvalido = new Usuario(2L, "", "Email", "987654321", LocalDate.now());

        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.guardarUsuario(usuarioInvalido));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void actualizarUsuario_DeberiaActualizarUsuarioSiExiste() {
        when(usuarioRepository.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuarioEjemplo));
        when(usuarioRepository.guardarActualizarUsuario(any())).thenReturn(usuarioEjemplo);

        Usuario resultado = usuarioService.actualizarUsuario(1L, usuarioEjemplo);

        assertNotNull(resultado);
        assertEquals("Jorge", resultado.getNombre());
    }

    @Test
    void actualizarUsuario_DeberiaLanzarExcepcionSiUsuarioNoExiste() {
        when(usuarioRepository.obtenerUsuarioPorId(99L)).thenReturn(Optional.empty());

        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.actualizarUsuario(99L, usuarioEjemplo));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void actualizarParcialmenteUsuario_DeberiaActualizarCamposEspecificos() {
        when(usuarioRepository.obtenerUsuarioPorId(1l)).thenReturn(Optional.of(usuarioEjemplo));
        when(usuarioRepository.guardarActualizarUsuario(any())).thenAnswer(
                invocation -> invocation.getArgument(0));

        Map<String, String> campoParaActualizar = Map.of("nombre", "Usuario test");

        Usuario resultado = usuarioService.actualizarParcialmenteUsuario(1L, campoParaActualizar);

        assertEquals("Usuario test", resultado.getNombre());
        assertEquals("jorge@gmail.com", resultado.getEmail());

    }

    @Test
    void actualizarParcialmenteUsuario_DeberiaLanzarExcepcionSiUsuarioNoExiste(){
        when(usuarioRepository.obtenerUsuarioPorId(99L)).thenReturn(Optional.empty());

        Map<String, String> campoParaActualizar = Map.of("nombre", "Usuario test");


        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.actualizarParcialmenteUsuario(99L, campoParaActualizar));
        assertEquals(404, exception.getErrorCode());

    }

    @Test
    void actualizarParcialmenteUsuario_DeberiaLanzarExcepcionSiCampoVacio(){
        when(usuarioRepository.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuarioEjemplo));

        Map<String, String> campoParaActualizar = Map.of("nombre", "");

        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.actualizarParcialmenteUsuario(1L, campoParaActualizar));
        assertEquals(400, exception.getErrorCode());

    }

    @Test
    void actualizarParcialmenteUsuario_DeberiaLanzarExcepcionSiCampoNoValido(){
        when(usuarioRepository.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuarioEjemplo));

        Map<String, String> campoParaActualizar = Map.of("apellido", "Perez");

        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.actualizarParcialmenteUsuario(1L, campoParaActualizar));
        assertEquals(400, exception.getErrorCode());

    }

    @Test
    void borrarUsuarioPorId_DeberiaEliminarUsuarioSiExiste() {
        when(usuarioRepository.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuarioEjemplo));
        doNothing().when(usuarioRepository).borrarUsuarioPorId(1L);

        assertDoesNotThrow(() -> usuarioService.borrarUsuarioPorId(1L));
    }

    @Test
    void borrarUsuarioPorId_DeberiaLanzarExcepcionSiUsuarioNoExiste() {
        when(usuarioRepository.obtenerUsuarioPorId(99L)).thenReturn(Optional.empty());

        UsuarioException exception = assertThrows(UsuarioException.class,
                () -> usuarioService.borrarUsuarioPorId(99L));
        assertEquals(404, exception.getErrorCode());
    }

    @Test
    void validarUsuarioAGuardar_DeberiaNoLanzarExcepcionSiUsuarioEsValido() {
        assertDoesNotThrow(() -> usuarioService.validarUsuarioAGuardar(usuarioEjemplo));
    }

    @Test
    void validarUsuarioAGuardar_DeberiaLanzarExcepcionSiNombreEsVacio() {
        Usuario usuarioInvalido = new Usuario(2L, "", "Email", "987654321", LocalDate.now());

        UsuarioException exception = assertThrows(UsuarioException.class, () -> usuarioService.validarUsuarioAGuardar(usuarioInvalido));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void validarUsuarioAGuardar_DeberiaLanzarExcepcionSiEmailEsVacio() {
        Usuario usuarioInvalido = new Usuario(2L, "Usuario de prueba", "", "987654321", LocalDate.now());

        UsuarioException exception = assertThrows(UsuarioException.class, () -> usuarioService.validarUsuarioAGuardar(usuarioInvalido));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void validarUsuarioAGuardar_DeberiaLanzarExcepcionSiTelefonoEsVacio() {
        Usuario usuarioInvalido = new Usuario(2L, "Usuario de prueba", "Email", "", LocalDate.now());

        UsuarioException exception = assertThrows(UsuarioException.class, () -> usuarioService.validarUsuarioAGuardar(usuarioInvalido));
        assertEquals(400, exception.getErrorCode());
    }

    @Test
    void validarUsuarioAGuardar_DeberiaLanzarExcepcionSiFechaRegistroEsNula() {
        Usuario usuarioInvalido = new Usuario(2L, "Usuario de prueba", "Email", "123456789", null);

        UsuarioException exception = assertThrows(UsuarioException.class, () -> usuarioService.validarUsuarioAGuardar(usuarioInvalido));
        assertEquals(400, exception.getErrorCode());
    }
}