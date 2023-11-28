package com.api.socialnetwork.dto;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto extends UsuarioCreateDto {

    @Schema(description = "Id do usuário")
    private Integer idUsuario;
}
