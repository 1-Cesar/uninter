package com.api.socialnetwork.repository;

import com.api.socialnetwork.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {
    List<Postagem> findAllByTituloContainingIgnoreCase (String titulo);

    List<Postagem> findByJuridicoId(Long juridicoId);
}
