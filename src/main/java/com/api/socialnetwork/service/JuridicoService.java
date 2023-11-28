package com.api.socialnetwork.service;

import com.api.socialnetwork.model.Juridico;
import com.api.socialnetwork.repository.JuridicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
@Service
public class JuridicoService {
    @Autowired
    private JuridicoRepository juridicoRepository;

    public Optional<Juridico> cadastrarEmpresa(Juridico juridico) {
        if (juridicoRepository.findByEmail(juridico.getEmail()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa já existe!", null);

        return Optional.of(juridicoRepository.save(juridico));
    }
}
