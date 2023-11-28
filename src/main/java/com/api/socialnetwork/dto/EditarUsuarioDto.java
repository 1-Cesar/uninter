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
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditarUsuarioDto {

    @Schema(description = "Nome do usuário", example = "Cecilia")
    @NotBlank
    @Size(min = 3, max = 50)
    private String nome;

    @Schema(description = "Telefone do usuário", example = "(11)91111-1111")
    @NotBlank
    @Size(min = 12, max = 15)
    private String telefone;

    @Schema(description = "Data de nascimento do usuário", example = "15/10/1960")
    @NotBlank
    @Size(min = 10, max = 10)
    private String dataNascimento;

    @Schema(description = "Tipo de deficiencia", example = "motora")
    @NotBlank
    @Size(min = 1, max = 90)
    private String tipoDeficiencia;
}