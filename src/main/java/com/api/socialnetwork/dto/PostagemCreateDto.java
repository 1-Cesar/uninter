package com.api.socialnetwork.dto;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostagemCreateDto {

    @Schema(description = "Titulo da postagem", example = "titulo 1")
    @NotBlank
    @Size(min = 2, max = 50)
    private String titulo;

    @Schema(description = "Texto da postagem", example = "texto 1")
    @NotBlank
    @Size(min = 2, max = 500)
    private String texto;

    @NotNull
    @Schema(description = "Data de publicação da postagem", hidden = true)
    private Date data = new java.sql.Date(System.currentTimeMillis());

    @NotNull
    private Set<String> temas;
}
