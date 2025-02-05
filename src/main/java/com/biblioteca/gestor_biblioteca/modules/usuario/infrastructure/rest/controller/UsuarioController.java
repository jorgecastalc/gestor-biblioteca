package com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.controller;

import com.biblioteca.gestor_biblioteca.modules.usuario.application.services.UsuarioService;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.mapper.UsuarioMapper;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.exceptions.UsuarioException;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.requests.UsuarioRequest;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.responses.UsuarioResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;


    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> obtenerUsuarios() {

        log.debug("[Controller] obteniendo lista de usuarios...");
        List<Usuario> usuariosList = usuarioService.obtenerUsuarios();

        log.info("[Controller] se han obtenido {} resultados", usuariosList.size());

        List<UsuarioResponse> usuarioResponseList = usuarioMapper.usuarioListToUsuarioResponseList(
                usuariosList);

        return ResponseEntity.ok(usuarioResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerUsuarioPorId(@PathVariable Long id) {

        log.debug("[Controller] obteniendo usuario con id {} ", id);

        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            return ResponseEntity.ok().body(usuarioMapper.usuarioDomainToUsuarioResponse(usuario));

        } catch (UsuarioException e) {
            log.error("[Controller] Error al obtener el usuario con id {} ", id, e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<Object> crearUsuario(@RequestBody UsuarioRequest usuarioRequest) {

        log.debug("[Controller] creando el usuario {} ", usuarioRequest.getNombre());
        Usuario usuario = usuarioMapper.usuarioRequestToUsuarioDomain(usuarioRequest);

        try {
            Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario);
            log.info("[Controller] el usuario {} se ha creado ", usuario.getNombre());
            UsuarioResponse usuarioResponse = usuarioMapper.usuarioDomainToUsuarioResponse(usuarioGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);

        } catch (UsuarioException e) {
            log.error("[Controller] Error al intentar guardar el usuario: ", e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarUsuario(@PathVariable Long id,
                                                  @RequestBody UsuarioRequest usuarioRequest) {

        log.debug("[Controller] actualizando usuario con id {} ", id);

        Usuario usuario = usuarioMapper.usuarioRequestToUsuarioDomain(usuarioRequest);

        try {
            UsuarioResponse usuarioActualizadoResponse = usuarioMapper.usuarioDomainToUsuarioResponse(
                    usuarioService.actualizarUsuario(id, usuario));

            log.info("[Controller] el usuario con id {} se ha actualizado", id);

            return ResponseEntity.ok(usuarioActualizadoResponse);
        } catch (UsuarioException e) {
            log.error("[Controller] Error al actualizar usuario con ID {}: {}", id, e.getMessage(),
                    e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> actualizarParcialmenteUsuario(@PathVariable Long id,
                                                              @RequestBody Map<String, String> camposActualizados) {

        log.debug("[Controller] se va a actualizar algun campo del usuario con id {}", id);

        try {

            Usuario usuarioActualizado = usuarioService.actualizarParcialmenteUsuario(id,
                    camposActualizados);
            UsuarioResponse usuarioResponse = usuarioMapper.usuarioDomainToUsuarioResponse(usuarioActualizado);

            log.info("[Controller] se han actualizado los siguientes campos del usuario con ID {}:",
                    id);
            camposActualizados.forEach(
                    (campo, valor) -> log.info("Campo {} actualizado a {}", campo, valor));

            return ResponseEntity.ok(usuarioResponse);
        } catch (UsuarioException e) {
            log.error("[Controller] Error en actualización parcial del usuario con ID {}: {}", id,
                    e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> borrarUsuario(@PathVariable Long id) {

        log.debug("[Controller] eliminando usuario con id {}", id);

        try {

            usuarioService.borrarUsuarioPorId(id);
            log.info("[Controller] el usuario con id {} se ha eliminado", id);
            return ResponseEntity.noContent().build();

        } catch (UsuarioException e) {
            log.error("[Controller] Error al eliminar el usuario con id {}: {}", id, e.getMessage(),e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }
}
