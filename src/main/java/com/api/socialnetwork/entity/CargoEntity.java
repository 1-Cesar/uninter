package com.api.socialnetwork.entity;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "cargo")
public class CargoEntity implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "seq_cargo", sequenceName = "seq_cargo", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cargo")
    @Column(name = "id_cargo")
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_cargo",
            joinColumns = @JoinColumn(name = "id_cargo"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<UsuarioEntity> usuarioEntities;

    @Override
    public String getAuthority() {
        return this.nome;
    }
}
