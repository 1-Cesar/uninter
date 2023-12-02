package com.api.socialnetwork.controller;

import com.api.socialnetwork.dto.CandidaturaDTO;
import com.api.socialnetwork.model.Candidatura;
import com.api.socialnetwork.model.Postagem;
import com.api.socialnetwork.model.Usuario;
import com.api.socialnetwork.repository.CandidaturaRepository;
import com.api.socialnetwork.repository.PostagemRepository;
import com.api.socialnetwork.repository.UsuarioRepository;
import com.api.socialnetwork.service.CandidaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/candidatura")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CandidaturaController {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PostagemRepository postagemRepository;

    @Autowired
    private CandidaturaService candidaturaService;

    @PostMapping("/criarCandidatura")
    public ResponseEntity<Candidatura> criarCandidatura(@RequestParam Long usuarioId, @RequestParam Long postagemId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Postagem postagem = postagemRepository.findById(postagemId).orElseThrow(() -> new RuntimeException("Postagem não encontrada"));

        Candidatura candidatura = new Candidatura();
        candidatura.setUsuario(usuario);
        candidatura.setPostagem(postagem);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(candidaturaRepository.save(candidatura));
    }

    @GetMapping("/recuperarPorPostagem/{idPostagem}")
    public ResponseEntity<List<CandidaturaDTO>> getCandidaturasPorPostagem(@PathVariable Long idPostagem) {
        List<CandidaturaDTO> candidaturas = candidaturaService.getCandidaturasComEmailPorPostagem(idPostagem);

        if (!candidaturas.isEmpty()) {
            return ResponseEntity.ok(candidaturas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
