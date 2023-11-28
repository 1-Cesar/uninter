package com.api.socialnetwork.repository;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.entity.PostagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<PostagemEntity, Integer> {

    List<PostagemEntity> findByIdPostagemAndIdUsuario(Integer idPostagem, Integer idUsuario);
}
