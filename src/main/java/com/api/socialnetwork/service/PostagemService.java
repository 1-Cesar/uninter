package com.api.socialnetwork.service;

import com.api.socialnetwork.model.Postagem2;
import com.api.socialnetwork.repository.PostagemRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostagemService {

    @Autowired
    private PostagemRepository2 postagemRepository2;

    public Postagem2 novoPost(Postagem2 postagem) {
        Postagem2 parametro = new Postagem2();

        parametro.setJuridicoID(postagem.getJuridicoID());
        parametro.setTexto(postagem.getTexto());
        parametro.setTitulo(postagem.getTitulo());

        return postagemRepository2.save(parametro);
    }
}

