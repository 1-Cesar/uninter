package com.api.socialnetwork.controller;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.dto.*;
import com.api.socialnetwork.enums.TipoUsuario;
import com.api.socialnetwork.exception.RegraDeNegocioException;
import com.api.socialnetwork.security.TokenService;
import com.api.socialnetwork.service.CredenciaisService;
import com.api.socialnetwork.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/usuario")
@Validated
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    private final AuthenticationManager authenticationManager;

    private final CredenciaisService credenciaisService;

    private final TokenService tokenService;

    @Operation(summary = "Recupera todos os usuários.", description = "Recupera todos os usuários presentes no banco de dados.")
    @GetMapping("/todos-usuarios")
    public ResponseEntity<List<UsuarioDto>> getAll() throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.getAll());
    }

    @Operation(summary = "Recupera os dados do usuário logado.", description = "Recupera as informações do usuário logado, informações estas presentes no banco de dados.")
    @GetMapping("/meu-usuario")
    public ResponseEntity<UsuarioDto> getByUser() throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.getByUser());
    }

    @Operation(summary = "Recupera um usuário com base no seu id.", description = "Recupera um usuário presente no banco de dados com base no seu id.")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable("idUsuario") Integer id) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.getById(id));
    }

    @Operation(summary = "Realiza o cadastro de um usuário.", description = "Realiza o cadastro de um usuário no banco de dados.")
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDto> register (@RequestBody @Valid UsuarioCreateDto usuarioCreateDto) throws RegraDeNegocioException, ParseException {

        return ResponseEntity.ok(usuarioService.create(usuarioCreateDto));
    }

    @Operation(summary = "Realiza o login de um determinado usuário.", description = "Realiza o login de um determinado usuário cadastrado no banco de dados.")
    @PostMapping("/login")
    public ResponseEntity<CredenciaisDto> getCredentials (@RequestBody LoginDto loginDto) throws RegraDeNegocioException {
           return ResponseEntity.ok(credenciaisService.getCredentials(loginDto));
    }

    @Operation(summary = "Altera o privilégio de um determinado usuário.", description = "Altera o privilégio de um determinado usuário no banco de dados.")
    @PostMapping("alterar-privilegio/{idUsuario}")
    public ResponseEntity<String> changePrivilege (@PathVariable("idUsuario") Integer idUsuario,
                                                           @RequestParam TipoUsuario tipoUsuario) throws RegraDeNegocioException {
        return ResponseEntity.ok(usuarioService.changePrivilege(idUsuario, tipoUsuario));
    }

    /*@Operation(summary = "Realiza o login de um determinado usuário.", description = "Realiza o login de um determinado usuário cadastrado no banco de dados.")
    @PostMapping("/login")
    public String auth (@RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getLogin(),
                        loginDto.getSenha()
                );

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        Object usuarioLogado = authentication.getPrincipal();
        UsuarioEntity usuarioEntity = (UsuarioEntity) usuarioLogado;
        String token = tokenService.getToken(usuarioEntity);
        return token;
    }*/

    @Operation(summary = "Edita os dados do usuário logado.", description = "Realiza edição dos dados do usuário logado.")
    @PutMapping("/editar")
    public UsuarioDto update(EditarUsuarioDto editarUsuarioDto) throws RegraDeNegocioException, ParseException {
        return usuarioService.update(editarUsuarioDto);
    }

    @Operation(summary = "Deleta um usuário com base no id informado.", description = "Deleta um usuário presente no banco de dados com base no id informado.")
    @DeleteMapping("/{idUsuario}")
    public void delete(@PathVariable("idUsuario") Integer id) throws RegraDeNegocioException {
        usuarioService.delete(id);
    }
}
