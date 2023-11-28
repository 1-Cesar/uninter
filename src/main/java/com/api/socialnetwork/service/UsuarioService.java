package com.api.socialnetwork.service;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v2
 */

import com.api.socialnetwork.dto.EditarUsuarioDto;
import com.api.socialnetwork.dto.UsuarioCreateDto;
import com.api.socialnetwork.dto.UsuarioDto;
import com.api.socialnetwork.entity.CargoEntity;
import com.api.socialnetwork.entity.UsuarioEntity;
import com.api.socialnetwork.enums.TipoUsuario;
import com.api.socialnetwork.exception.RegraDeNegocioException;
import com.api.socialnetwork.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final ObjectMapper objectMapper;

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioDto> getAll() throws RegraDeNegocioException {
        if (usuarioRepository.findAll().size() == 0) {
            throw new RegraDeNegocioException("Desculpe, nenhum usuário foi localizado!");
        } else {
            return usuarioRepository.findAll().stream()
                    .map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDto.class))
                    .collect(Collectors.toList());
        }
    }

    public UsuarioDto getById(Integer id) throws RegraDeNegocioException {
        localizarUsuario(id);
        UsuarioEntity usuarioEntity = usuarioRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Desculpe, nenhum usuário foi localizado!"));

        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEntity, UsuarioDto.class);

        return usuarioDto;
    }

    public UsuarioDto getByUser() throws RegraDeNegocioException {
        Integer idLogado = getIdLoggedUser();

        UsuarioEntity usuarioEntity = usuarioRepository.findById(idLogado)
                .orElseThrow(() -> new RegraDeNegocioException("Desculpe, nenhum usuário foi localizado!"));

        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioEntity, UsuarioDto.class);

        return usuarioDto;
    }

    public UsuarioDto create(UsuarioCreateDto usuarioCreateDto) throws RegraDeNegocioException, ParseException {
        if (usuarioCreateDto.getTipo() == TipoUsuario.MASTER) {
            throw new RegraDeNegocioException("Tipo de usuário Inválido");
        } else {
            UsuarioEntity usuarioEntity = new UsuarioEntity();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNascimento = formatter.parse(usuarioCreateDto.getDataNascimento());

            usuarioEntity.setDataNascimento(dataNascimento);
            usuarioEntity.setTelefone(usuarioCreateDto.getTelefone());
            usuarioEntity.setNome(usuarioCreateDto.getNome());
            usuarioEntity.setTipoDeficiencia(usuarioCreateDto.getTipoDeficiencia());
            usuarioEntity.setLogin(usuarioCreateDto.getLogin());
            usuarioEntity.setSenha(usuarioCreateDto.getSenha());
            usuarioEntity.setTipo(usuarioCreateDto.getTipo());
            usuarioEntity.setFoto(usuarioCreateDto.getFoto());
            usuarioEntity.setSenha(criptografarSenha(usuarioCreateDto.getSenha()));

            CargoEntity cargoEntity = new CargoEntity();

            List<CargoEntity> cargoEntities = new ArrayList<>();

            if (usuarioEntity.getTipo().toString().equals("USUARIO")) {

                cargoEntity.setId(2);

            } else if (usuarioEntity.getTipo().toString().equals("EMPRESA")) {

                cargoEntity.setId(1);

            }  else if (usuarioEntity.getTipo().toString().equals("MASTER")) {

                cargoEntity.setId(3);

            } else {
                throw new RegraDeNegocioException("Tipo de usuário inválido!");
            }

            cargoEntities.add(cargoEntity);

            usuarioEntity.setCargos(cargoEntities);

            UsuarioDto usuarioDto = objectMapper.convertValue(usuarioRepository.save(usuarioEntity), UsuarioDto.class);

            usuarioDto.setDataNascimento(usuarioCreateDto.getDataNascimento());

            return usuarioDto;
        }
    }

    public UsuarioDto update(EditarUsuarioDto editarUsuarioDto) throws RegraDeNegocioException, ParseException {
        Integer idLogado = getIdLoggedUser();

        UsuarioEntity usuarioEntity = localizarUsuario(idLogado);

        usuarioEntity.setIdUsuario(idLogado);
        usuarioEntity.setNome(editarUsuarioDto.getNome());
        usuarioEntity.setTelefone(editarUsuarioDto.getTelefone());
        usuarioEntity.setTipoDeficiencia(editarUsuarioDto.getTipoDeficiencia());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dataNascimento = formatter.parse(editarUsuarioDto.getDataNascimento());

        usuarioEntity.setDataNascimento(dataNascimento);

        UsuarioDto usuarioDto = objectMapper.convertValue(usuarioRepository.save(usuarioEntity), UsuarioDto.class);

        usuarioDto.setDataNascimento(editarUsuarioDto.getDataNascimento());

        return usuarioDto;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        Integer idLogado = getIdLoggedUser();
        UsuarioEntity usuarioEntity = localizarUsuario(idLogado);

        if (usuarioEntity.getTipo() == TipoUsuario.MASTER) {
            usuarioEntity = localizarUsuario(id);
            usuarioRepository.delete(usuarioEntity);
        } else if (!id.equals(idLogado)) {
            throw new RegraDeNegocioException("Você não pode deletar outro usuário!");
        } else {
            usuarioRepository.delete(usuarioEntity);
        }
    }

    public String changePrivilege(Integer id, TipoUsuario tipoUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = localizarUsuario(id);
        CargoEntity cargoEntity = new CargoEntity();
        List<CargoEntity> cargoEntities = new ArrayList<>();

        if (tipoUsuario.equals(TipoUsuario.MASTER)) {
            cargoEntity.setId(1);
            usuarioEntity.setTipo(TipoUsuario.MASTER);
        } else {
            cargoEntity.setId(2);
            usuarioEntity.setTipo(TipoUsuario.USUARIO);
        }

        cargoEntities.add(cargoEntity);
        usuarioEntity.setCargos(cargoEntities);

        usuarioRepository.save(usuarioEntity);

        return "Privilégio alterado com Sucesso!";
    }

    public UsuarioEntity localizarUsuario(Integer id) throws RegraDeNegocioException {
        return usuarioRepository.findAll().stream()
                                        .filter(usuarioEntity -> usuarioEntity.getIdUsuario().equals(id))
                                        .findFirst()
                                        .orElseThrow(() -> new RegraDeNegocioException("Desculpe, nenhum usuário foi localizado!"));
    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String criptografia = bCryptPasswordEncoder.encode(senha);
        return criptografia;
    }

    public Integer getIdLoggedUser() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }
}
