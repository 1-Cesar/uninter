package com.api.socialnetwork.controller;

import com.api.socialnetwork.model.UsuarioLogin;
import com.api.socialnetwork.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> authentication(@RequestBody Optional<UsuarioLogin> usuarioLogin) {
        return loginService.logarUsuario(usuarioLogin)
                .map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
