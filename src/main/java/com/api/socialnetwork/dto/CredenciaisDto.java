package com.api.socialnetwork.dto;
/**
 * @since 15/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredenciaisDto {

    private Integer id;

    private String nome;

    private String token;

    private String foto;

    private TipoUsuario tipoUsuario;
}
