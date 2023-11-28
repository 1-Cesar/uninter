package com.api.socialnetwork.controller;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.dto.TemaCreateDto;
import com.api.socialnetwork.dto.TemaDto;
import com.api.socialnetwork.exception.RegraDeNegocioException;
import com.api.socialnetwork.service.TemaService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tema")
@Validated
@RequiredArgsConstructor
public class TemaController {

    private final TemaService temaService;

    @Operation(summary = "Recupera todos os temas do usuário logado.", description = "Recupera todos os temas presentes no banco de dados com base no usuário logado.")
    @GetMapping("/meus-temas")
    public ResponseEntity<List<TemaDto>> getAll() {
        return ResponseEntity.ok(temaService.getAll());
    }

    @Operation(summary = "Realiza o cadastro de um novo tema.", description = "Cadastra um novo tema no banco de dados.")
    @PostMapping("/cadastro")
    public ResponseEntity<TemaDto> create(@RequestBody TemaCreateDto temaCreateDto) {
        return ResponseEntity.ok(temaService.create(temaCreateDto));
    }

    @Operation(summary = "Deleta um tema com base no id informado.", description = "Deleta um tema no banco de dados com base no id informado.")
    @DeleteMapping("/{idTema}")
    public void delete(@PathVariable("idTema") Integer idTema) throws RegraDeNegocioException {
        temaService.delete(idTema);
    }
}
