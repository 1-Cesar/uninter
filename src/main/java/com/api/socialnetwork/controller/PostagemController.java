package com.api.socialnetwork.controller;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.dto.PostagemCreateDto;
import com.api.socialnetwork.dto.PostagemDto;
import com.api.socialnetwork.exception.RegraDeNegocioException;
import com.api.socialnetwork.service.PostagemService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/postagem")
@Validated
@RequiredArgsConstructor
public class PostagemController {

    private final PostagemService postagemService;

    @Operation(summary = "Recupera todas as postagens.", description = "Recupera todas as postagens do banco de dados.")
    @GetMapping("/todas-postagens")
    public ResponseEntity<List<PostagemDto>> getAll() throws RegraDeNegocioException {
        return ResponseEntity.ok(postagemService.getAll());
    }

    @Operation(summary = "Recupera as postagens do usuário logado.", description = "Recupera as postagens presentes no banco com base no usuário logado.")
    @GetMapping("/minhas-postagens")
    public ResponseEntity<List<PostagemDto>> getMyPosts() {
        return ResponseEntity.ok(postagemService.getMyPosts());
    }

    @Operation(summary = "Realiza uma postagem.", description = "Salva uma postagem no banco de dados.")
    @PostMapping("/postar")
    public ResponseEntity<PostagemDto> create(@RequestBody PostagemCreateDto postagemCreateDto) throws RegraDeNegocioException {
        return ResponseEntity.ok(postagemService.create(postagemCreateDto));
    }

    @Operation(summary = "Altera uma postagem com base no id da mesma.", description = "Altera uma postagem presente no banco de dados com base no id informado.")
    @PutMapping("/{idPostagem}")
    public ResponseEntity<PostagemDto> update(@Valid @PathVariable("idPostagem") Integer idPostagem,
                                              PostagemCreateDto postagemCreateDto) throws RegraDeNegocioException {
        return ResponseEntity.ok(postagemService.update(postagemCreateDto, idPostagem));
    }

    @Operation(summary = "Deleta uma postagem com base no id da mesma.", description = "Deleta uma postagem no banco de dados com base no id informado.")
    @DeleteMapping("/{idPostagem}")
    public void delete(@PathVariable("idPostagem") Integer idPostagem) throws RegraDeNegocioException {
        postagemService.delete(idPostagem);
    }
}
