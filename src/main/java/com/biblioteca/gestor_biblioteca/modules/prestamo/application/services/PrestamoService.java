package com.biblioteca.gestor_biblioteca.modules.prestamo.application.services;

import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.repository.LibroRepository;
import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.model.Prestamo;
import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.repository.PrestamoRepository;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.exceptions.PrestamoException;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class PrestamoService {


    private final PrestamoRepository prestamoRepository;
    private final LibroRepository libroRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Prestamo> obtenerPrestamos() {

        log.info("[Service] obteniendo lista de prestamos");

        return prestamoRepository.obtenerPrestamos();
    }

    public Prestamo obtenerPrestamoPorId(Long id) {

        log.info("[Service] obteniendo prestamo con id {}", id);

        return prestamoRepository.obtenerPrestamoPorId(id)
                .orElseThrow(() -> {
                            log.warn("[Service] no se encontró ningun prestamo con id {}", id);
                            return new PrestamoException(404, "Prestamo no encontrado");
                        }
                );
    }

    public Prestamo guardarPrestamo(Prestamo prestamo) {
        log.info("[Service] creando un nuevo prestamo");

        validarPrestamo(prestamo);

        Long libroId = prestamo.getLibro().getId();
        Long usuarioId = prestamo.getUsuario().getId();

        validarLibroPrestado(libroId);

        Libro libro = libroRepository.obtenerLibroPorId(libroId)
                .orElseThrow(() -> {
                    log.warn("[Service] no se encontró ningun libro con id {}",
                            libroId);
                    return new PrestamoException(404, "Libro no encontrado");
                });
        Usuario usuario = usuarioRepository.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> {
                    log.warn("[Service] no se encontró ningun usuario con id {}",
                            usuarioId);
                    return new PrestamoException(404, "Usuario no encontrado");
                });

        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);

        log.info("[Service] prestamo creado con usuario {} y libro {}", usuario.getNombre(),
                libro.getTitulo());

        return prestamoRepository.guardarActualizarPrestamo(prestamo);
    }

    public Prestamo actualizarPrestamo(Long id, Prestamo prestamo) {
        log.info("[Service] actualizando el prestamo con id {}", id);

        validarPrestamo(prestamo);

        Long libroId = prestamo.getLibro().getId();
        Long usuarioId = prestamo.getUsuario().getId();

        validarLibroPrestado(libroId);

        Libro libro = libroRepository.obtenerLibroPorId(libroId)
                .orElseThrow(() -> {
                    log.warn("[Service] no se encontró ningun libro con id {}",
                            libroId);
                    return new PrestamoException(404, "Libro no encontrado");
                });

        Usuario usuario = usuarioRepository.obtenerUsuarioPorId(usuarioId)
                .orElseThrow(() -> {
                    log.warn("[Service] no se encontró ningun usuario con id {}",
                            usuarioId);
                    return new PrestamoException(404, "Usuario no encontrado");
                });

        Prestamo prestamoParaActualizar = prestamoRepository.obtenerPrestamoPorId(id)
                .orElseThrow(() -> {
                    log.warn("[Service] no se encontró ningun prestamo con id {}", id);
                    return new PrestamoException(404, "Prestamo no encontrado");
                });


        prestamoParaActualizar.setUsuario(usuario);
        prestamoParaActualizar.setLibro(libro);
        prestamoParaActualizar.setFechaPrestamo(prestamo.getFechaPrestamo());
        prestamoParaActualizar.setFechaDevolucion(prestamo.getFechaDevolucion());

        log.info("[Service] prestamo con id {} actualizado correctamente", id);

        return prestamoRepository.guardarActualizarPrestamo(prestamoParaActualizar);
    }

    public Prestamo actualizarParcialmentePrestamo(Long id,
                                                   Map<String, String> camposActualizados) {

        log.info("[Service] actualizando parcialmente el prestamo con id {}", id);

        Prestamo prestamoParaActualizar = prestamoRepository.obtenerPrestamoPorId(id)
                .orElseThrow(() -> {
                    log.warn("[Service] no se encontró ningun prestamo con id {}", id);
                    return new PrestamoException(404, "Prestamo no encontrado");
                });

        camposActualizados.forEach((campo, valor) -> {

            if (valor == null || valor.isBlank()) {
                throw new PrestamoException(400, "El campo " + campo + " no puede estar vacio");
            }
            switch (campo) {
                case "usuarioId" -> {
                    Long nuevoUsuarioId = Long.parseLong(valor);
                    Usuario nuevoUsuario = usuarioRepository.obtenerUsuarioPorId(nuevoUsuarioId)
                            .orElseThrow(() -> {
                                log.warn("[Service] no se encontró ningun usuario con id {}",
                                        nuevoUsuarioId);
                                return new PrestamoException(404, "Usuario no encontrado");
                            });
                    prestamoParaActualizar.setUsuario(nuevoUsuario);
                }
                case "libroId" -> {
                    Long nuevoLibroId = Long.parseLong(valor);

                    validarLibroPrestado(nuevoLibroId);

                    Libro nuevoLibro = libroRepository.obtenerLibroPorId(nuevoLibroId)
                            .orElseThrow(() -> {
                                log.warn("[Service] no se encontró ningun libro con id {}",
                                        nuevoLibroId);
                                return new PrestamoException(404, "Libro no encontrado");
                            });
                    prestamoParaActualizar.setLibro(nuevoLibro);
                }
                case "fechaPrestamo" -> {
                    try {
                        prestamoParaActualizar.setFechaPrestamo(LocalDate.parse(valor));
                    } catch (Exception e) {
                        throw new PrestamoException(400, "Formato de fecha de prestamo inválido");
                    }
                }
                case "fechaDevolucion" -> {
                    try {
                        prestamoParaActualizar.setFechaDevolucion(LocalDate.parse(valor));
                    } catch (Exception e) {
                        throw new PrestamoException(400, "Formato de fecha de devolucion inválido");
                    }
                }
                default -> throw new PrestamoException(400, "Campo no valido");
            }

        });
        log.info("[Service] prestamo con id {} actualizado parcialmente", id);
        return prestamoRepository.guardarActualizarPrestamo(prestamoParaActualizar);
    }

    public void borrarPrestamoPorId(Long id) {

        log.info("[Service] eliminando el prestamo con id {}", id);

        if (prestamoRepository.obtenerPrestamoPorId(id).isPresent()) {
            prestamoRepository.borrarPrestamoPorId(id);
            log.info("[Service] el prestamo con id {} ha sido eliminado", id);
        } else {
            log.warn("[Service] no se encontró ningun prestamo con id {}", id);
            throw new PrestamoException(404, "El prestamo no se ha podido encontrar");
        }
    }

    private void validarPrestamo(Prestamo prestamo) {
        if (prestamo.getLibro() == null || prestamo.getLibro().getId() == null) {
            throw new PrestamoException(400, "El préstamo debe tener un libro asignado");
        }
        if (prestamo.getUsuario() == null || prestamo.getUsuario().getId() == null) {
            throw new PrestamoException(400, "El préstamo debe tener un usuario asignado");
        }
        if (prestamo.getFechaPrestamo() == null) {
            throw new PrestamoException(400, "La fecha de préstamo no puede estar vacía");
        }
        if (prestamo.getFechaDevolucion() == null) {
            throw new PrestamoException(400, "La fecha de devolución no puede estar vacía");
        }
        if (prestamo.getFechaDevolucion().isBefore(prestamo.getFechaPrestamo())) {
            throw new PrestamoException(400, "La fecha de devolución no puede ser anterior a la fecha de préstamo");
        }
    }

    private void validarLibroPrestado(Long id){
        boolean libroEnPrestamo = prestamoRepository.obtenerPrestamosPorLibroId(id).stream()
                .anyMatch(p -> p.getFechaDevolucion().isAfter(LocalDate.now()));

        if (libroEnPrestamo) {
            log.warn("[Service] el libro con id {} ya está prestado actualmente", id);
            throw new PrestamoException(400, "El libro ya está prestado y no puede ser asignado nuevamente.");
        }

    }
}
