package com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.controller;

import com.biblioteca.gestor_biblioteca.modules.prestamo.application.services.PrestamoService;
import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.model.Prestamo;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.mapper.PrestamoMapper;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.exceptions.PrestamoException;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.requests.PrestamoRequest;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.responses.PrestamoResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/prestamos")
@AllArgsConstructor
public class PrestamoController {

    private final PrestamoMapper prestamoMapper;
    private final PrestamoService prestamoService;

    @GetMapping
    public ResponseEntity<List<PrestamoResponse>> obtenerPrestamos() {

        log.debug("[Controller] obteniendo lista de prestamos...");
        List<Prestamo> prestamoList = prestamoService.obtenerPrestamos();

        log.info("[Controller] se han obtenido {} resultados", prestamoList.size());

        List<PrestamoResponse> prestamoListResponse = prestamoMapper.prestamoListToPrestamoResponseList(
                prestamoList);

        return ResponseEntity.ok(prestamoListResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerPrestamoPorId(@PathVariable Long id) {

        log.debug("[Controller] obteniendo prestamo con id {} ", id);

        try {
            Prestamo prestamo = prestamoService.obtenerPrestamoPorId(id);

            PrestamoResponse prestamoResponse = prestamoMapper.prestamoDomainToPrestamoResponse(
                    prestamo);

            return ResponseEntity.ok(prestamoResponse);

        } catch (PrestamoException e) {
            log.warn("[Controller] El prestamo con id {}, no se ha encontrado", id, e.getMessage());
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> guardarPrestamo(
            @RequestBody PrestamoRequest prestamoRequest) {

        log.debug("[Controller] creando el prestamo {} ", prestamoRequest.getId());
        Prestamo prestamo = prestamoMapper.prestamoRequestToPrestamoDomain(prestamoRequest);

        try {
            Prestamo prestamoGuardado = prestamoService.guardarPrestamo(prestamo);
            log.info("[Controller] el prestamo {} se ha creado ", prestamoGuardado.getId());

            PrestamoResponse prestamoResponse = prestamoMapper.prestamoDomainToPrestamoResponse(
                    prestamoGuardado);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamoResponse);

        } catch (PrestamoException e) {
            log.error("[Controller] Error al intentar guardar el prestamo: ", e.getMessage(), e);
            return ResponseEntity.status((e.getErrorCode())).body(e.getMessage());

        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarPrestamo(@PathVariable Long id,
                                                     @RequestBody PrestamoRequest prestamoRequest) {

        log.debug("[Controller] actualizando prestamo con ID {} ", id);

        Prestamo prestamo = prestamoMapper.prestamoRequestToPrestamoDomain(prestamoRequest);
        try {
            Prestamo prestamoActualizado = prestamoService.actualizarPrestamo(id, prestamo);

            PrestamoResponse prestamoActualizadoResponse = prestamoMapper.prestamoDomainToPrestamoResponse(
                    prestamoActualizado);

            return ResponseEntity.ok(prestamoActualizadoResponse);
        } catch (PrestamoException e) {
            log.error("[Controller] Error al actualizar prestamo con ID {}: {}", id, e.getMessage(),
                    e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> actualizarParcialmentePrestamo(@PathVariable Long id,
                                                                 @RequestBody Map<String, String> prestamoRequest) {

        log.debug("[Controller] se va a actualizar algun campo del prestamo con id {}", id);

        try {
            Prestamo prestamoActualizado = prestamoService.actualizarParcialmentePrestamo(id,
                    prestamoRequest);
            PrestamoResponse prestamoActualizadoResponse = prestamoMapper.prestamoDomainToPrestamoResponse(
                    prestamoActualizado);
            log.info(
                    "[Controller] se han actualizado los siguientes campos del prestamo con ID {}:",
                    id);
            prestamoRequest.forEach(
                    (campo, valor) -> log.info("Campo {} actualizado a {}", campo, valor));

            return ResponseEntity.ok(prestamoActualizadoResponse);
        } catch (PrestamoException e) {
            log.error("[Controller] Error en actualización parcial del prestamo con ID {}: {}", id,
                    e.getMessage(), e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> borrarPrestamoPorId(@PathVariable Long id) {

        log.debug("[Controller] eliminando prestamo con id {}", id);

        try {
            prestamoService.borrarPrestamoPorId(id);
            log.info("[Controller] el prestamo con id {} se ha eliminado", id);
            return ResponseEntity.noContent().build();

        } catch (PrestamoException e) {
            log.error("[Controller] Error al eliminar el prestamo con id {}: {}", id, e.getMessage(),e);
            return ResponseEntity.status(e.getErrorCode()).body(e.getMessage());
        }
    }

}
