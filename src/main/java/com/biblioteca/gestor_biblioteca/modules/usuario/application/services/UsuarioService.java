package com.biblioteca.gestor_biblioteca.modules.usuario.application.services;


import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.model.Prestamo;
import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.repository.PrestamoRepository;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.repository.UsuarioRepository;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.exceptions.UsuarioException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PrestamoRepository prestamoRepository;

    public List<Usuario> obtenerUsuarios(){

        log.info("[Service] obteniendo lista de usuarios");

        return usuarioRepository.obtenerUsuarios();
    }

    public Usuario obtenerUsuarioPorId(Long id) throws UsuarioException{
        log.info("[Service] obteniendo usuario con id {}", id);

        return usuarioRepository.obtenerUsuarioPorId(id).orElseThrow(() -> {
            log.warn("[Service] no se encontró ningun usuario con id {}", id);
            return new UsuarioException(404,
                    "No hay resultados para el usuario con id " + id);
        });
    }

    public Usuario guardarUsuario(Usuario usuario) {

        validarUsuarioAGuardar(usuario);

        log.info("[Service] creando usuario nuevo");
        return usuarioRepository.guardarActualizarUsuario(usuario);

    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {

        validarUsuarioAGuardar(usuario);

        log.info("[Service] actualizando el usuario {}", usuario.getNombre());

        Usuario usuarioActualizado = usuarioRepository.obtenerUsuarioPorId(id).orElseThrow(
                () -> new UsuarioException(404,"Usuario con id " + id + " no encontrado"));

        usuarioActualizado.setNombre(usuario.getNombre());
        usuarioActualizado.setEmail(usuario.getEmail());
        usuarioActualizado.setTelefono(usuario.getTelefono());
        usuarioActualizado.setFechaRegistro(usuario.getFechaRegistro());

        return usuarioRepository.guardarActualizarUsuario(usuarioActualizado);
    }

    public Usuario actualizarParcialmenteUsuario(Long id, Map<String, String> camposActualizados) {

        log.info("[Service] actualizando el usuario con id {}", id);

        Usuario usuarioActualizado = usuarioRepository.obtenerUsuarioPorId(id).orElseThrow(
                () -> new UsuarioException(404,"Usuario con id " + id + " no encontrado"));

        camposActualizados.forEach((campo, valor) -> {

            if(valor == null ||valor.isBlank()){
                throw new UsuarioException(400,"El campo " + campo +  " no puede estar vacio");
            }
            switch (campo) {
                case "nombre" -> usuarioActualizado.setNombre(valor);
                case "email" -> usuarioActualizado.setEmail(valor);
                case "telefono" -> usuarioActualizado.setTelefono(valor);
                case "fechaRegistro" ->
                        usuarioActualizado.setFechaRegistro(LocalDate.parse(valor));
                default -> throw new UsuarioException(400,"Campo no válido " + campo);
            }
        });

        return usuarioRepository.guardarActualizarUsuario(usuarioActualizado);

    }

    public void borrarUsuarioPorId(Long id) {

        List<Prestamo> prestamos = prestamoRepository.obtenerPrestamosPorUsuarioId(id);

        boolean tienePrestamosActivos = prestamos.stream()
                .anyMatch(prestamo -> prestamo.getFechaDevolucion().isAfter(LocalDate.now()));

        if (tienePrestamosActivos) {
            throw new UsuarioException(400, "No se puede eliminar el usuario, tiene préstamos activos.");
        }
        prestamos.forEach(prestamo -> {
            prestamo.setUsuario(null);
            prestamoRepository.guardarActualizarPrestamo(prestamo);
        });

        log.info("[Service] eliminando el usuario con id {}", id);
        if (usuarioRepository.obtenerUsuarioPorId(id).isPresent()) {

            usuarioRepository.borrarUsuarioPorId(id);
            log.info("[Service] el usuario se ha eliminado");

        } else {
            log.warn("[Service] no se encontró el usuario con id {}", id);
            throw new UsuarioException(404, "No existe ningun usuario con id " + id);
        }

    }

    public void validarUsuarioAGuardar(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {

            throw new UsuarioException(400, "El nombre del usuario no puede estar vacio");
        }
        if (usuario.getEmail() == null|| usuario.getEmail().isBlank()) {

            throw new UsuarioException(400, "El email del usuario no puede estar vacio");
        }
        if (usuario.getTelefono() == null|| usuario.getTelefono().isBlank()) {

            throw new UsuarioException(400, "El telefono del usuario no puede estar vacio");
        }
        if (usuario.getFechaRegistro() == null) {

            throw new UsuarioException(400, "La fecha de registro del usuario no puede estar vacio");
        }
    }

}
