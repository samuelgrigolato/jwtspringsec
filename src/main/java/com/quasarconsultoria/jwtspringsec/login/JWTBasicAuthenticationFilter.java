package com.quasarconsultoria.jwtspringsec.login;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JWTBasicAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain chain) throws IOException, ServletException {

        Cookie token = WebUtils.getCookie(httpRequest, "token");
        if (token == null) {
            chain.doFilter(httpRequest, httpResponse);
            return;
        }

        try {

            String jwt = token.getValue();

            DecodedJWT decodedJwt = JWT.require(Algorithm.HMAC256("algosecretoaqui"))
                    .build()
                    .verify(jwt);

            String login = decodedJwt.getClaim("login").asString();

            List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(login, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // chamada autenticada
            chain.doFilter(httpRequest, httpResponse);

        } catch (JWTVerificationException ex) {
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

    }
}
