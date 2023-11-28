package com.api.socialnetwork.dto;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Schema(description = "Login do usuário", example = "joao")
    @NotBlank
    @Size(min = 3, max = 15)
    private String login;

    @Schema(description = "Senha do usuário", example = "sua senha")
    @NotBlank
    @Size(min = 3, max = 15)
    private String senha;

    @Schema(hidden = true)
    private Integer id;

    @Schema(hidden = true)
    private String nome;

    @Schema(hidden = true)
    private String token;

    @Schema(hidden = true)
    private String foto;

    @Schema(hidden = true)
    private TipoUsuario tipoUsuario;
}
