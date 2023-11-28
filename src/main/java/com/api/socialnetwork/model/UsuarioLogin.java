package com.api.socialnetwork.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UsuarioLogin {
    @NotNull
    private String email;

    @NotNull
    private String senha;

    @NotNull
    private Boolean logado;
}