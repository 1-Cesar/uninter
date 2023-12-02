package com.api.socialnetwork.service;

import com.api.socialnetwork.dto.CandidaturaDTO;
import com.api.socialnetwork.repository.CandidaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidaturaService {
    @Autowired
    private CandidaturaRepository candidaturaRepository;

    public List<CandidaturaDTO> getCandidaturasComEmailPorPostagem(Long idPostagem) {
        return candidaturaRepository.findCandidaturasWithUserEmail(idPostagem);
    }
}
