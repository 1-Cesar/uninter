package com.api.socialnetwork.service;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.dto.TemaCreateDto;
import com.api.socialnetwork.dto.TemaDto;
import com.api.socialnetwork.entity.TemaEntity;
import com.api.socialnetwork.entity.UsuarioEntity;
import com.api.socialnetwork.enums.TipoUsuario;
import com.api.socialnetwork.exception.RegraDeNegocioException;
import com.api.socialnetwork.repository.TemaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemaService {

    private final ObjectMapper objectMapper;

    private final TemaRepository temaRepository;

    private final UsuarioService usuarioService;

    public List<TemaDto> getAll() {
        Integer idLoggedUser = usuarioService.getIdLoggedUser();

        return temaRepository.findAllByIdUsuario(idLoggedUser).stream()
                .map(temaEntity -> objectMapper.convertValue(temaEntity, TemaDto.class))
                .collect(Collectors.toList());
    }

    public TemaEntity createComPostagem(TemaCreateDto temaCreateDto) {
        temaCreateDto.setNome(temaCreateDto.getNome().toLowerCase());

        Integer idLogado = usuarioService.getIdLoggedUser();

        try {
            Integer verificaExistenciaTag = temaRepository.findByNomeCount(temaCreateDto.getNome());

            if(verificaExistenciaTag > 0 ){
                throw new RegraDeNegocioException("Este tema já existe!");
            }

            if(temaCreateDto.getNome().isBlank() || temaCreateDto.getNome().isEmpty() ){
                throw new RegraDeNegocioException("Nome do tema não pode ser nulo ou vazio");
            }
        } catch (RegraDeNegocioException e) {
            e.getMessage();
        }

        TemaEntity temaEntity = objectMapper.convertValue(temaCreateDto, TemaEntity.class);
        temaEntity.setIdUsuario(idLogado);
        TemaEntity novoTema = temaRepository.save(temaEntity);

        return novoTema;
    }

    public TemaDto create(TemaCreateDto temaCreateDto) {
        temaCreateDto.setNome(temaCreateDto.getNome().toLowerCase());
        Integer idLogado = usuarioService.getIdLoggedUser();

        try {
            Integer verificaExistenciaTag = temaRepository.findByNomeCount(temaCreateDto.getNome());

            if(verificaExistenciaTag > 0 ){
                throw new RegraDeNegocioException("Este tema já existe!");
            }

            if(temaCreateDto.getNome().isBlank() || temaCreateDto.getNome().isEmpty() ){
                throw new RegraDeNegocioException("Nome do tema não pode ser nulo ou vazio");
            }
        } catch (RegraDeNegocioException e) {
            e.getMessage();
        }

        TemaEntity temaEntity = objectMapper.convertValue(temaCreateDto, TemaEntity.class);
        temaEntity.setIdUsuario(idLogado);

        return objectMapper.convertValue(temaRepository.save(temaEntity), TemaDto.class);
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        localizarTema(id);

        Integer idLoggedUser = usuarioService.getIdLoggedUser();
        UsuarioEntity usuarioEntity = usuarioService.localizarUsuario(idLoggedUser);

        if (usuarioEntity.getTipo() == TipoUsuario.USUARIO) {
            localizarTemaUsuarioLogado(id, idLoggedUser);
            temaRepository.deleteById(id);
        } else {
            temaRepository.deleteById(id);
        }
    }

    public TemaEntity localizarTema(Integer id) throws RegraDeNegocioException {
        return temaRepository.findAll().stream()
                .filter(temaEntity -> temaEntity.getIdTema().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Desculpe, nenhum tema foi localizado!"));
    }

    private TemaEntity localizarTemaUsuarioLogado(Integer idTema, Integer idUsuario) throws RegraDeNegocioException {
        return temaRepository.findAllByIdTemaAndIdUsuario(idTema, idUsuario).stream()
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Tema não encontrado!"));
    }

    public Optional<TemaEntity> findByNomeTema(String nomeTag) {
        return temaRepository.findAllByNomeContainingIgnoreCase(nomeTag);
    }
}
