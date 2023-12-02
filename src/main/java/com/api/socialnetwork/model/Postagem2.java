package com.api.socialnetwork.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "postagem")
public class Postagem2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String texto;

    private Long juridicoID;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPost = new java.sql.Date(System.currentTimeMillis());
}