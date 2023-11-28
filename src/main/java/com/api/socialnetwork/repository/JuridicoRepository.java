package com.api.socialnetwork.repository;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.model.Juridico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JuridicoRepository extends JpaRepository<Juridico, Long> {
    List<Juridico> findAllByNomeContainingIgnoreCase (String nome);

    Optional<Juridico> findByEmail(String email);
}
