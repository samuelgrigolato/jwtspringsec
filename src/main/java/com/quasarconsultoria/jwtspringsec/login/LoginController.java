package com.quasarconsultoria.jwtspringsec.login;

import com.quasarconsultoria.jwtspringsec.model.Usuario;
import com.quasarconsultoria.jwtspringsec.model.UsuariosRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/login")
class LoginController {

    private UsuariosRepository usuariosRepository;

    LoginController(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @PostMapping
    void login(@RequestBody CredenciaisDTO credenciais, HttpSession session) {
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
        session.setAttribute("idUsuarioLogado", usuario.getId());
    }

    private String criptografar(String senha) {
        return DigestUtils.sha1Hex(senha + "algoaqui");
    }

}
