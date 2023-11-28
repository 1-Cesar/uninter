package com.api.socialnetwork.service;

import com.api.socialnetwork.model.Usuario;
import com.api.socialnetwork.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

        return Optional.of(usuarioRepository.save(usuario));
    }
}
