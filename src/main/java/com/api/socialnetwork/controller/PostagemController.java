package com.api.socialnetwork.controller;

import com.api.socialnetwork.model.Postagem;
import com.api.socialnetwork.model.Postagem2;
import com.api.socialnetwork.repository.PostagemRepository;
import com.api.socialnetwork.service.PostagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/postagem")
@CrossOrigin(value = "*", allowedHeaders = "*")
public class PostagemController {
    @Autowired
    private PostagemRepository postagemRepository;

    @Autowired
    private PostagemService postagemService;

    @GetMapping("/recuperarPostagens")
    public ResponseEntity<List<Postagem>> getAll() {
        return ResponseEntity.ok(postagemRepository.findAll());
    }

    @GetMapping("/recuperarPostagensEmpresa/{juridicoid}")
    public ResponseEntity<List<Postagem>> recuperarPostagensPorJuridicoId(@PathVariable Long juridicoid) {
        List<Postagem> postagens = postagemRepository.findByJuridicoId(juridicoid);

        if (!postagens.isEmpty()) {
            return ResponseEntity.ok(postagens);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{titulo}")
    public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
    }

    @PostMapping("/novoPost")
    public ResponseEntity<Postagem2> post(@Valid @RequestBody Postagem2 postagem) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postagemService.novoPost(postagem));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        postagemRepository.deleteById(id);
    }
}
