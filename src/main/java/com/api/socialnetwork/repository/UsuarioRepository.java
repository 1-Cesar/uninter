package com.api.socialnetwork.repository;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

    Optional<UsuarioEntity> findByLogin(String login);
}
