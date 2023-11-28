package com.api.socialnetwork.model;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "postagem")
public class Postagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String texto;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPost = new java.sql.Date(System.currentTimeMillis());

    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Juridico juridico;
}