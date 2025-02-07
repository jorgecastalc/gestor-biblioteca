package com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.mapper;

import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.entity.UsuarioJpaEntity;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.requests.UsuarioRequest;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.rest.responses.UsuarioResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario usuarioJpaEntityToUsuarioDomain(UsuarioJpaEntity usuarioJpaEntity);
    UsuarioJpaEntity usuarioDomainToUsuarioJpaEntity(Usuario usuario);

    Usuario usuarioResponseToUsuarioDomain(UsuarioResponse usuarioResponse);
    UsuarioResponse usuarioDomainToUsuarioResponse(Usuario usuario);

    List<Usuario> usuarioResponseListToUsuarioList(List<UsuarioResponse> usuarios);
    List<UsuarioResponse> usuarioListToUsuarioResponseList(List<Usuario> usuarios);

    Usuario usuarioRequestToUsuarioDomain(UsuarioRequest usuarioRequest);
    UsuarioRequest usuarioDomainToUsuarioRequest(Usuario usuario);

}
