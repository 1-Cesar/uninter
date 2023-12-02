package com.api.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "candidatura")
public class Candidatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "postagem_id")
    @JsonBackReference
    private Postagem postagem;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCandidatura = new java.sql.Date(System.currentTimeMillis());
}
