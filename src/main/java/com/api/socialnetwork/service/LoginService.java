package com.api.socialnetwork.service;

import com.api.socialnetwork.model.Juridico;
import com.api.socialnetwork.model.Usuario;
import com.api.socialnetwork.model.UsuarioLogin;
import com.api.socialnetwork.repository.JuridicoRepository;
import com.api.socialnetwork.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
public class LoginService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JuridicoRepository juridicoRepository;

    public Optional<UsuarioLogin> logarUsuario(Optional<UsuarioLogin> usuarioLogin) {
        if (usuarioLogin.isPresent()) {
            String email = usuarioLogin.get().getEmail();
            String senha = usuarioLogin.get().getSenha();

            Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

            if (usuario.isPresent() && senha.equals(usuario.get().getSenha())) {
                usuarioLogin.get().setTipo(usuario.get().getTipo());
                usuarioLogin.get().setId(usuario.get().getId());
                return usuarioLogin;
            }

            Optional<Juridico> juridico = juridicoRepository.findByEmail(email);

            if (juridico.isPresent() && senha.equals(juridico.get().getSenha())) {
                usuarioLogin.get().setTipo(juridico.get().getTipo());
                usuarioLogin.get().setId(juridico.get().getId());
                return usuarioLogin;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos!", null);
    }
}
