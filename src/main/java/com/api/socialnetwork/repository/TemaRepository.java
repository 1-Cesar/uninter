package com.api.socialnetwork.repository;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.entity.TemaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemaRepository extends JpaRepository<TemaEntity, Integer> {

    List<TemaEntity> findAllByIdTemaAndIdUsuario(Integer idTema, Integer idUsuario);

    List<TemaEntity> findAllByIdUsuario(Integer idUsuario);

    Optional<TemaEntity> findAllByNomeContainingIgnoreCase(String nome);

    @Query(value = " SELECT COUNT(*)" +
            " FROM tema t" +
            " WHERE nome = :nome")
    Integer findByNomeCount(@Param("nome") String nome);
}
