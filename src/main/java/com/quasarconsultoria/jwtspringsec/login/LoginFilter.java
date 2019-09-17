package com.quasarconsultoria.jwtspringsec.login;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(1)
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpServletRequest httpRequest = (HttpServletRequest)request;

        if (!httpRequest.getServletPath().startsWith("/api")) {
            // requisição para recurso estático
            chain.doFilter(request, response);
            return;
        }

        if (httpRequest.getServletPath().startsWith("/api/login")) {
            // o usuário está tentando se autenticar
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("idUsuarioLogado") == null) {
            // chamada sem autenticação
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        // chamada autenticada
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
