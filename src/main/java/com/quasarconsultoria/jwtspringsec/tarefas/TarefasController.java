package com.quasarconsultoria.jwtspringsec.tarefas;

import com.quasarconsultoria.jwtspringsec.comum.AcessoNegadoException;
import com.quasarconsultoria.jwtspringsec.model.Tarefa;
import com.quasarconsultoria.jwtspringsec.model.TarefasRepository;
import com.quasarconsultoria.jwtspringsec.model.Usuario;
import com.quasarconsultoria.jwtspringsec.model.UsuariosRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tarefas")
class TarefasController {

    private TarefasRepository tarefasRepository;
    private UsuariosRepository usuariosRepository;

    TarefasController(TarefasRepository tarefasRepository,
                      UsuariosRepository usuariosRepository) {
        this.tarefasRepository = tarefasRepository;
        this.usuariosRepository = usuariosRepository;
    }

    @GetMapping
    List<TarefaDTO> buscarTodas(HttpSession session) {
        Usuario usuario = getUsuarioLogado(session);
        return this.tarefasRepository
                .findByUsuario(usuario).stream()
                .map(TarefaDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    TarefaDetalhadaDTO buscarPorId(@PathVariable("id") Integer id, HttpSession session) {
        Tarefa tarefa = this.tarefasRepository.findById(id).get();
        Integer idUsuarioLogado = (Integer)session.getAttribute("idUsuarioLogado");
        if (!tarefa.getUsuario().getId().equals(idUsuarioLogado)) {
            throw new AcessoNegadoException();
        }
        return new TarefaDetalhadaDTO(tarefa);
    }

    @PostMapping
    void cadastrar(@RequestBody NovaTarefaDTO tarefa, HttpSession session) {
        Usuario usuario = getUsuarioLogado(session);
        Tarefa entidade = new Tarefa();
        entidade.setUsuario(usuario);
        entidade.setDescricao(tarefa.getDescricao());
        entidade.setCriadaEm(LocalDateTime.now());
        this.tarefasRepository.save(entidade);
    }

    private Usuario getUsuarioLogado(HttpSession session) {
        Integer idUsuarioLogado = (Integer)session.getAttribute("idUsuarioLogado");
        return this.usuariosRepository.findById(idUsuarioLogado).get();
    }

}
