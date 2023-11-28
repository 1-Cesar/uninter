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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemaDto extends TemaCreateDto{

    @Schema(description = "Id do tema", hidden = true)
    private Integer idTema;
}
