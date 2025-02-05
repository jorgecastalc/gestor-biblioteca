package com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.repository;

import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.repository.UsuarioRepository;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.entity.UsuarioJpaEntity;
import com.biblioteca.gestor_biblioteca.modules.usuario.infrastructure.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public List<Usuario> obtenerUsuarios() {

        return usuarioJpaRepository.findAll().stream().map(
                usuarioMapper::usuarioJpaEntityToUsuarioDomain).toList();

    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioJpaRepository.findById(id).map(usuarioMapper::usuarioJpaEntityToUsuarioDomain);
    }

    @Override
    public Usuario guardarActualizarUsuario(Usuario usuario) {

        UsuarioJpaEntity usuarioJpaEntity = usuarioMapper.usuarioDomainToUsuarioJpaEntity(usuario);

        return usuarioMapper.usuarioJpaEntityToUsuarioDomain(usuarioJpaRepository.save(usuarioJpaEntity));
    }

    @Override
    public void borrarUsuarioPorId(Long id) {
        usuarioJpaRepository.deleteById(id);

    }
}
