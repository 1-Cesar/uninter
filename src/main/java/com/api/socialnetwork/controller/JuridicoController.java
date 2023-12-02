package com.api.socialnetwork.controller;

import com.api.socialnetwork.model.Juridico;
import com.api.socialnetwork.repository.JuridicoRepository;
import com.api.socialnetwork.service.JuridicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empresa")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JuridicoController {
    @Autowired
    private JuridicoRepository juridicoRepository;

    @Autowired
    private JuridicoService juridicoService;

    @GetMapping
    public ResponseEntity<List<Juridico>> getAll() {
        return ResponseEntity.ok(juridicoRepository.findAll());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Juridico> post(@Valid @RequestBody Juridico email) {
        return juridicoService.cadastrarEmpresa(email)
                .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
                .orElse(ResponseEntity.status(400).build());
    }

    @DeleteMapping("/delete/{id}")
    public void deletarEmpresa (@PathVariable Long id) {
        juridicoRepository.deleteById(id);
    }
}
