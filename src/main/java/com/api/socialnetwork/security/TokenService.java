package com.api.socialnetwork.security;
/**
 * @since 13/09/2022
 * @autor Cesar Augusto
 * @version v1
 */

import com.api.socialnetwork.entity.UsuarioEntity;
import com.api.socialnetwork.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    private final UsuarioService userLoginService;

    private static final String KEY_CARGOS = "roles";

    public String getToken(UsuarioEntity userLoginEntity) {

        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.valueOf(expiration));

        List<String> listaDeCargos = userLoginEntity.getCargos().stream()
                .map(cargoEntity -> cargoEntity.getNome())
                .toList();

        String token = Jwts.builder()
                .setIssuer("socialnetwork-api")
                .claim(Claims.ID, userLoginEntity.getIdUsuario())
                .claim(KEY_CARGOS, listaDeCargos)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return TokenAuthenticationFilter.BEARER + token;
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {

        if (token == null) {
            return null;
        }

        Claims payload = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        Integer idUsuario = payload.get(Claims.ID, Integer.class);

        if (idUsuario != null) {
            List<String> cargos = payload.get(KEY_CARGOS, List.class);

            List<SimpleGrantedAuthority> cargosGrantedAuthority = cargos.stream()
                    .map(cargo -> new SimpleGrantedAuthority(cargo))
                    .toList();

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(idUsuario, null, cargosGrantedAuthority);

            return usernamePasswordAuthenticationToken;
        }
        return null;
    }
}
