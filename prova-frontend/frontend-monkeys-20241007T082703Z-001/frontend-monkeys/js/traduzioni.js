function changeLanguage(lang) {
  // Memorizza la lingua selezionata in localStorage
  localStorage.setItem('selectedLanguage', lang);

  // Carica il file JSON corrispondente alla lingua
  fetch(`../traduzioni/${lang}.json`)
    .then(response => response.json())
    .then(translations => {
      // Trova tutti gli elementi con l'attributo data-translate
      const elementsToTranslate = document.querySelectorAll("[data-translate]");

      // Sostituisci il testo di ogni elemento con la traduzione appropriata
      elementsToTranslate.forEach(element => {
        const key = element.getAttribute("data-translate");
        if (translations[key]) {
          element.textContent = translations[key];
        }
      });

      // Cambia l'immagine dello slogan in base alla lingua selezionata
      const sloganImage = document.getElementById("immagine-slogan");
      if (lang === 'it') {
        sloganImage.src = '../image/slogan-italiano.png';
      } else if (lang === 'en') {
        sloganImage.src = '../image/slogan-inglese.png';
      } else if (lang === 'es') {
        sloganImage.src = '../image/slogan-spagnolo.png';
      }

      const seguiciImage = document.getElementById("immagine-seguici");
      if (lang === 'it') {
        seguiciImage.src = '../image/seguici-italiano.png';
      } else if (lang === 'en') {
        seguiciImage.src = '../image/seguici-inglese.png';
      } else if (lang === 'es') {
        seguiciImage.src = '../image/seguici-spagnolo.png';
      }
    })
    .catch(error => console.error('Errore nel caricamento delle traduzioni:', error));
}

// Imposta la lingua predefinita (italiano) quando la pagina viene caricata
document.addEventListener("DOMContentLoaded", function() {
  const savedLanguage = localStorage.getItem('selectedLanguage') || 'it'; // Italiano di default
  changeLanguage(savedLanguage);
});