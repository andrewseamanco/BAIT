function getPanels() {
  const accordian = document.getElementsByClassName('accordion');
  for (let i = 0; i < accordian.length; i++) {
    accordian[i].addEventListener('click', function() {
      this.classList.toggle('active');
      var panel = this.nextElementSibling;
      if (panel.style.maxHeight) {
        panel.style.maxHeight = null;
      } else {
        panel.style.maxHeight = panel.scrollHeight + 'px';
      }
    });
  }
}
