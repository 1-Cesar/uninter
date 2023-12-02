package com.api.socialnetwork.repository;

import com.api.socialnetwork.dto.CandidaturaDTO;
import com.api.socialnetwork.model.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    @Query("SELECT new com.api.socialnetwork.dto.CandidaturaDTO(c.id, u.email) FROM Candidatura c JOIN c.usuario u WHERE c.postagem.id = :idPostagem")
    List<CandidaturaDTO> findCandidaturasWithUserEmail(Long idPostagem);
}
