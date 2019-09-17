
function carregarTarefas() {
  Fetch.get("/api/tarefas").then(tarefas => {
    const $ul = document.getElementById("tarefas");
    $ul.innerHTML = ""; // limpa o elemento
    tarefas.forEach(tarefa => {
      const $li = document.createElement("li");
      $li.innerText = tarefa.descricao;
      const $a = document.createElement("a");
      $a.onclick = () => detalhar(tarefa.id);
      $a.innerText = " (detalhar)";
      $li.appendChild($a);
      $ul.appendChild($li);
    });
  });
}

function detalhar(id) {
  Fetch.get(`/api/tarefas/${id}`).then(tarefa => {
    const $detalhes = document.getElementById("detalhes");
    $detalhes.innerHTML = ""; // limpa o elemento

    const $titulo = document.createElement("h1");
    $titulo.innerText = `Tarefa ${id}`;
    $detalhes.appendChild($titulo);

    const $descricao = document.createElement("p");
    $descricao.innerText = tarefa.descricao;
    $detalhes.appendChild($descricao);

    const $cadastradaEm = document.createElement("p");
    $cadastradaEm.innerText = `Cadastrada em ${tarefa.criadaEm}`;
    $detalhes.appendChild($cadastradaEm);
  });
}

function cadastrar() {
  const dados = {
    descricao: document.getElementsByName("descricao")[0].value
  };
  Fetch.post("/api/tarefas", dados).then(() => {
    const $form = document.getElementById("formulario");
    $form.reset();
    carregarTarefas();
  });
}

carregarTarefas();
