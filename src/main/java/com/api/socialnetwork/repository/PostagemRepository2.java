package com.api.socialnetwork.repository;

import com.api.socialnetwork.model.Postagem;
import com.api.socialnetwork.model.Postagem2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostagemRepository2 extends JpaRepository<Postagem2, Long> {
}
