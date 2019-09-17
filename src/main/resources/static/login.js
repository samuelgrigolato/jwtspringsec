
function entrar() {
  const data = {
    usuario: document.getElementsByName("usuario")[0].value,
    senha: document.getElementsByName("senha")[0].value
  };
  Fetch.post("/api/login", data)
    .then(() => {
      window.location = "/";
    })
    .catch(_ => {
      alert("Credenciais inválidas.");
    });
}
