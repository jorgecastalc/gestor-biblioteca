package com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.mapper;

import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.model.Prestamo;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.entity.PrestamoJpaEntity;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.requests.PrestamoRequest;
import com.biblioteca.gestor_biblioteca.modules.prestamo.infrastructure.rest.responses.PrestamoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrestamoMapper {


    Prestamo prestamoJpaEntityToPrestamoDomain(PrestamoJpaEntity prestamoJpaEntity);
    PrestamoJpaEntity prestamoDomainToPrestamoJpaEntity(Prestamo prestamo);

    Prestamo prestamoResponseToPrestamoDomain(PrestamoResponse prestamoResponse);
    PrestamoResponse prestamoDomainToPrestamoResponse(Prestamo prestamo);

    List<Prestamo> prestamoResponseListToPrestamoList(List<PrestamoResponse> prestamos);
    List<PrestamoResponse> prestamoListToPrestamoResponseList(List<Prestamo> prestamos);

    @Mapping(target = "libro.id", source = "libroId")
    @Mapping(target = "usuario.id", source = "usuarioId")
    Prestamo prestamoRequestToPrestamoDomain(PrestamoRequest prestamoRequest);

    PrestamoRequest prestamoDomainToPrestamoRequest(Prestamo prestamo);

}
