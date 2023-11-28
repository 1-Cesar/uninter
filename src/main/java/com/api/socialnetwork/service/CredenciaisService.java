package com.api.socialnetwork.service;

import com.api.socialnetwork.dto.CredenciaisDto;
import com.api.socialnetwork.dto.LoginDto;
import com.api.socialnetwork.entity.UsuarioEntity;
import com.api.socialnetwork.exception.RegraDeNegocioException;
import com.api.socialnetwork.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredenciaisService {

    private final UsuarioService usuarioService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public CredenciaisDto getCredentials(LoginDto loginDto) throws RegraDeNegocioException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getLogin(),
                        loginDto.getSenha()
                );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Object usuarioLogado = authentication.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) usuarioLogado;
        String token = tokenService.getToken(usuarioEntity);

        CredenciaisDto credenciaisDto = new CredenciaisDto();

        credenciaisDto.setFoto(usuarioEntity.getFoto());
        credenciaisDto.setId(usuarioEntity.getIdUsuario());
        credenciaisDto.setNome(usuarioEntity.getNome());
        credenciaisDto.setTipoUsuario(usuarioEntity.getTipo());
        credenciaisDto.setToken(token);

        return credenciaisDto;
    }
}
