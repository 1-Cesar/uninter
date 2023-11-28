package com.api.socialnetwork.controller;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.model.Usuario;
import com.api.socialnetwork.model.UsuarioLogin;
import com.api.socialnetwork.repository.UsuarioRepository;
import com.api.socialnetwork.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> post(@Valid @RequestBody Usuario email) {
        return usuarioService.cadastrarUsuario(email)
                .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
                .orElse(ResponseEntity.status(400).build());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUsuarios (@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }
}
