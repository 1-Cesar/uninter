package com.api.socialnetwork.service;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.dto.*;
import com.api.socialnetwork.entity.PostagemEntity;
import com.api.socialnetwork.entity.TemaEntity;
import com.api.socialnetwork.entity.UsuarioEntity;
import com.api.socialnetwork.enums.TipoUsuario;
import com.api.socialnetwork.exception.RegraDeNegocioException;
import com.api.socialnetwork.repository.PostagemRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostagemService {

    private final ObjectMapper objectMapper;

    private final PostagemRepository postagemRepository;

    private final UsuarioService usuarioService;

    private final TemaService temaService;

    public List<PostagemDto> getAll() throws RegraDeNegocioException {
        if (postagemRepository.findAll().size() == 0) {
            throw new RegraDeNegocioException("Desculpe, nenhuma postagem foi localizada!");
        } else {
            return postagemRepository.findAll().stream()
                    .map(postagemEntity -> getPostagemDto(postagemEntity))
                    .collect(Collectors.toList());
        }
    }

    public List<PostagemDto> getMyPosts() {
        Integer idLogado = usuarioService.getIdLoggedUser();

        return postagemRepository.findAll().stream()
                .filter(postagemEntity -> postagemEntity.getUsuario().getIdUsuario().equals(idLogado))
                .map(postagemEntity -> getPostagemDto(postagemEntity))
                .collect(Collectors.toList());
    }

    public PostagemDto create(PostagemCreateDto postagemCreateDto) throws RegraDeNegocioException {

        Integer idLogado = usuarioService.getIdLoggedUser();
        UsuarioEntity usuarioEntity = usuarioService.localizarUsuario(idLogado);

        PostagemEntity postagemEntity = objectMapper.convertValue(postagemCreateDto, PostagemEntity.class);

        postagemEntity.setUsuario(usuarioEntity);
        postagemEntity.setIdUsuario(idLogado);
        postagemEntity.setData(new java.sql.Date(System.currentTimeMillis()));
        postagemEntity.setTemas(getTemaDtos(postagemCreateDto));
        postagemRepository.save(postagemEntity);

        return getPostagemDto(postagemEntity);
    }

    public PostagemDto update(PostagemCreateDto postagemCreateDto, Integer idPostagem) throws RegraDeNegocioException {

        Integer idLogado = usuarioService.getIdLoggedUser();
        UsuarioEntity usuarioEntity = usuarioService.localizarUsuario(idLogado);
        PostagemEntity postagemLocalizada = localizarPostagemUsuarioLogado(idPostagem, idLogado);

        postagemLocalizada.setUsuario(usuarioEntity);
        postagemLocalizada.setIdUsuario(idLogado);
        postagemLocalizada.setTemas(getTemaDtos(postagemCreateDto));
        postagemLocalizada.setTitulo(postagemCreateDto.getTitulo());
        postagemLocalizada.setTexto(postagemCreateDto.getTexto());

        postagemRepository.save(postagemLocalizada);

        return getPostagemDto(postagemLocalizada);
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        localizarPostagem(id);

        Integer idLoggedUser = usuarioService.getIdLoggedUser();
        UsuarioEntity usuarioEntity = usuarioService.localizarUsuario(idLoggedUser);

        if(usuarioEntity.getTipo() == TipoUsuario.USUARIO) {
            localizarPostagemUsuarioLogado(id, idLoggedUser);
            postagemRepository.deleteById(id);
        } else {
            postagemRepository.deleteById(id);
        }
    }

    public PostagemEntity localizarPostagem(Integer id) throws RegraDeNegocioException {
        return postagemRepository.findAll().stream()
                .filter(postagemEntity -> postagemEntity.getIdPostagem().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Desculpe, nenhuma postagem foi encontrada!"));
    }

    private PostagemEntity localizarPostagemUsuarioLogado(Integer idPostagem, Integer idUsuario) throws RegraDeNegocioException {
        return postagemRepository.findByIdPostagemAndIdUsuario(idPostagem, idUsuario).stream()
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Postagem não encontrada!"));
    }

    private Set<TemaEntity> getTemaDtos(PostagemCreateDto postagemCreateDto) {
        Set<TemaEntity> temaEntities = postagemCreateDto.getTemas().stream()
                .map(temaString -> {
                    Optional<TemaEntity> nomeTag = temaService.findByNomeTema(temaString);
                    if (nomeTag.isPresent()) {
                        return nomeTag.get();
                    }
                    else {
                        return temaService.createComPostagem(new TemaCreateDto(temaString));
                        }
                    })
                .collect(Collectors.toSet());
        return temaEntities;
    }

    private PostagemDto getPostagemDto (PostagemEntity postagemEntity) {
        PostagemDto postagemDto = objectMapper.convertValue(postagemEntity, PostagemDto.class);

        if(postagemEntity.getTemas() != null) {
            postagemDto.setTemas(postagemEntity.getTemas().stream()
                    .map(temaEntity -> temaEntity.getNome())
                    .collect(Collectors.toSet()));
        }
        return postagemDto;
    }
}