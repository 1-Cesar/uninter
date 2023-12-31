package com.api.socialnetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Hidden
    @ManyToOne
    @JsonIgnoreProperties("postagem")
    private Juridico juridico;

    @Hidden
    @OneToMany(mappedBy = "postagem", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Candidatura> candidaturas;
}