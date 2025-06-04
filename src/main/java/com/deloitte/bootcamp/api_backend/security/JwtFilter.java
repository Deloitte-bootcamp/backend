package com.deloitte.bootcamp.api_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_PATHS = Arrays.asList(
            "/user/register",
            "/auth/login",
            "/reset/request",
            "/reset/change",
            "/v3/api-docs",
            "/v3/api-docs/",
            "/v3/api-docs/*",
            "/swagger-ui",
            "/swagger-ui/",
            "/swagger-ui/*",
            "/swagger-ui.html"
    );

    @Value("${secret_key}")
    private String SECRET;

    private SecretKey SECRET_KEY;

    @PostConstruct
    public void init() {
        // Converte a string secreta em SecretKey
        SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    @SuppressWarnings({ "null", "deprecation" })
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        for (String prefix : PUBLIC_PATHS) {
            if (path.equals(prefix) || path.startsWith(prefix + "/")) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = header.substring(7);

        try {

            Claims claims = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Extração do nome do usuário
            String username = claims.getSubject();
            // Extração das roles
            String roles = claims.get("roles", String.class);

            List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority(roles)
            );

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);

            // Definimos o token no contexto de segurança
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (ExpiredJwtException e) {
            System.out.println("Token expirado!");
        } catch (UnsupportedJwtException e) {
            System.out.println("Formato de token não suportado!");
        } catch (MalformedJwtException e) {
            System.out.println("Token malformado!");
        } catch (SignatureException e) {
            System.out.println("Assinatura inválida!");
        } catch (Exception e) {
            System.out.println("Erro desconhecido: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
