function changeLanguage(lang) {
  // Store the selected language in localStorage
  localStorage.setItem('selectedLanguage', lang);

  // Load the corresponding JSON file for the selected language
  fetch(`../traduzioni/${lang}.json`)
    .then(response => response.json())
    .then(translations => {
      // Find all elements with the data-translate attribute
      const elementsToTranslate = document.querySelectorAll("[data-translate]");

      // Replace the text of each element with the appropriate translation
      elementsToTranslate.forEach(element => {
        const key = element.getAttribute("data-translate");
        if (translations[key]) {
          element.textContent = translations[key];
        }
      });

      // Change the slogan image based on the selected language
      const sloganImage = document.getElementById("immagine-slogan");
      if (lang === 'it') {
        sloganImage.src = '/image/slogan-italiano.png';
      } else if (lang === 'en') {
        sloganImage.src = '/image/slogan-inglese.png';
      } else if (lang === 'es') {
        sloganImage.src = '/image/slogan-spagnolo.png';
      }

      const seguiciImage = document.getElementById("immagine-seguici");
      if (lang === 'it') {
        seguiciImage.src = '/image/seguici-italiano.png';
      } else if (lang === 'en') {
        seguiciImage.src = '/image/seguici-inglese.png';
      } else if (lang === 'es') {
        seguiciImage.src = '/image/seguici-spagnolo.png';
      }
    })
    .catch(error => console.error('Errore nel caricamento delle traduzioni:', error));
}

// Set the default language (Italian) when the page loads
document.addEventListener("DOMContentLoaded", function() {
  const savedLanguage = localStorage.getItem('selectedLanguage') || 'it'; // Italiano di default
  changeLanguage(savedLanguage);
});