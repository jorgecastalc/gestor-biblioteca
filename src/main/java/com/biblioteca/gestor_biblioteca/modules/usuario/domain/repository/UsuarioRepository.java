package com.biblioteca.gestor_biblioteca.modules.usuario.domain.repository;

import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    List<Usuario> obtenerUsuarios();
    Optional<Usuario> obtenerUsuarioPorId(Long id);
    Usuario guardarActualizarUsuario(Usuario usuario);
    void borrarUsuarioPorId(Long id);

}
