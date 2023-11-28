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
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity(name = "postagem")
public class PostagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postagem_seq")
    @SequenceGenerator(name = "postagem_seq", sequenceName = "seq_postagem", allocationSize = 1)
    @Column(name = "id_postagem")
    private Integer idPostagem;

    @Column(name = "id_usuario", insertable = false, updatable = false)
    private Integer idUsuario;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "titulo")
    private String titulo;

    @NotNull
    @Size (min = 2, max = 500)
    @Column(name = "texto")
    private String texto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_postagem")
    private Date data = new java.sql.Date(System.currentTimeMillis());

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "postagem_tema",
            joinColumns = @JoinColumn(name = "id_postagem"),
            inverseJoinColumns = @JoinColumn(name = "id_tema")
    )
    private Set<TemaEntity> temas;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuario;
}