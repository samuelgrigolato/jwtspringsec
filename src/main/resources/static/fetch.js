
Fetch = {

  get: url => {
    return new Promise((resolve, reject) => {
      fetch(url)
        .then(resp => {
          if (resp.status == 401) {
            window.location = "/login.html";
          } else if (resp.status !== 200) {
            reject("Não foi possível executar a operação.");
          } else {
            resp.json().then(dados => resolve(dados));
          }
        })
        .catch(_ => {
          alert("Servidor indisponível.");
        });
    });
  },

  post: (url, dados) => {
    return new Promise((resolve, reject) => {
      fetch(url, {
        headers: {
          'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify(dados)
      })
      .then(resp => {
        if (resp.status == 401) {
          window.location = "/login.html";
        } else if (resp.status !== 200) {
          reject("Não foi possível executar a operação.");
        } else {
          resolve();
        }
      })
      .catch(_ => {
        alert("Servidor indisponível.");
      });
    });
  }

}
