function getPanels() {
  const accordion = document.getElementsByClassName('accordion');
  for (fold of accordion) {
    fold.addEventListener('click', function() {
      this.classList.toggle('active');
      const panel = this.nextElementSibling;
      if (panel.style.maxHeight) {          //when accordion button is clicked, if panel is visible closes panel by setting maxHeight to null
        panel.style.maxHeight = null;
      } else {
        panel.style.maxHeight = panel.scrollHeight + 'px';  //when accordion button is clicked, if panel is not visible, set maxHeight so panel is displayed
      }
    });
  }
}
