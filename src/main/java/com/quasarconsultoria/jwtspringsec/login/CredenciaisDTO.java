package com.quasarconsultoria.jwtspringsec.login;

class CredenciaisDTO {

    private String usuario;
    private String senha;

    String getUsuario() {
        return usuario;
    }

    void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    String getSenha() {
        return senha;
    }

    void setSenha(String senha) {
        this.senha = senha;
    }
}
