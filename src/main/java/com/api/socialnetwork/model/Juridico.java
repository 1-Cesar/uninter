package com.api.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "juridico")
public class Juridico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @Schema(example = "email@email.com.br")
    @NotNull
    @Email
    private String email;

    @NotNull
    private String senha;

    @NotNull
    private String cnpj;

    @NotNull
    private String tipo;

    @Hidden
    @OneToMany(mappedBy = "juridico", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties("juridico")
    @JsonBackReference
    private List<Postagem> postagens;
}
