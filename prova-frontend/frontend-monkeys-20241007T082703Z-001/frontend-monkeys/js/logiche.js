  // Funzione per aprire la finestra di Accesso
  document.querySelector('[data-translate="accesso"]').addEventListener('click', function(event) {
    event.preventDefault();
    document.getElementById('modal-accesso').classList.add('attiva');
    document.getElementById('modal-registrazione').classList.remove('attiva'); // Nasconde registrazione
  });

  // Funzione per aprire la finestra di Registrazione
  document.querySelector('[data-translate="registrati"]').addEventListener('click', function(event) {
    event.preventDefault();
    document.getElementById('modal-registrazione').classList.add('attiva');
    document.getElementById('modal-accesso').classList.remove('attiva'); // Nasconde accesso
  });

  // Funzione per chiudere il modal specifico
  function chiudiModal(id) {
    document.getElementById(id).classList.remove('attiva');
  }