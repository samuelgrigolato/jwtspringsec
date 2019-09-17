package com.quasarconsultoria.jwtspringsec.tarefas;

import com.quasarconsultoria.jwtspringsec.model.Tarefa;

import java.time.LocalDateTime;

class TarefaDetalhadaDTO {

    private String descricao;
    private LocalDateTime criadaEm;

    TarefaDetalhadaDTO(Tarefa entidade) {
        this.descricao = entidade.getDescricao();
        this.criadaEm = entidade.getCriadaEm();
    }

    public String getDescricao() {
        return descricao;
    }

    void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    void setCriadaEm(LocalDateTime criadaEm) {
        this.criadaEm = criadaEm;
    }
}
