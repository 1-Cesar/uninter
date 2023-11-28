package com.api.socialnetwork.entity;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity(name = "tema")
public class TemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tema_seq")
    @SequenceGenerator(name = "tema_seq", sequenceName = "seq_tema", allocationSize = 1)
    @Column(name = "id_tema")
    private Integer idTema;

    @Column(name = "id_usuario", updatable = false)
    private Integer idUsuario;

    @NotNull
    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "postagem_tema",
            joinColumns = @JoinColumn(name = "id_tema"),
            inverseJoinColumns = @JoinColumn(name = "id_postagem")
    )
    private Set<PostagemEntity> postagem;
}

