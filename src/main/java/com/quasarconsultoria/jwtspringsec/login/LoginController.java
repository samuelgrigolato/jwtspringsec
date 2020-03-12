package com.quasarconsultoria.jwtspringsec.login;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.quasarconsultoria.jwtspringsec.model.Usuario;
import com.quasarconsultoria.jwtspringsec.model.UsuariosRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

//@RestController
//@RequestMapping("/api/login")
class LoginController {

    private UsuariosRepository usuariosRepository;

    LoginController(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @PostMapping
    void login(@RequestBody CredenciaisDTO credenciais, HttpServletResponse response) {
        Optional<Usuario> talvezUsuario = this.usuariosRepository
                .findByLogin(credenciais.getUsuario());
        if (talvezUsuario.isEmpty()) {
            throw new CredenciaisInvalidasException();
        }
        String senhaCriptografada = criptografar(credenciais.getSenha());
        Usuario usuario = talvezUsuario.get();
        if (!usuario.getSenha().equals(senhaCriptografada)) {
            throw new CredenciaisInvalidasException();
        }

        String jwt = JWT.create()
                .withClaim("idUsuarioLogado", usuario.getId())
                .sign(Algorithm.HMAC256("algosecretoaqui"));

        Cookie cookie = new Cookie("token", jwt);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 30); // 30 minutos
        response.addCookie(cookie);
    }

    private String criptografar(String senha) {
        return DigestUtils.sha1Hex(senha + "algoaqui");
    }

}
