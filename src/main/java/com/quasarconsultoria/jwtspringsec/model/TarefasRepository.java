package com.quasarconsultoria.jwtspringsec.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TarefasRepository extends CrudRepository<Tarefa, Integer> {

    List<Tarefa> findByUsuario(Usuario usuario);

}
