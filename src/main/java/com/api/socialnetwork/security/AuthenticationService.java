package com.api.socialnetwork.security;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.entity.UsuarioEntity;
import com.api.socialnetwork.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UsuarioEntity> optionalUserLogin = usuarioService.findByLogin(username);

        return optionalUserLogin
                .orElseThrow(() -> new UsernameNotFoundException("Usuário Inválido"));
    }
}