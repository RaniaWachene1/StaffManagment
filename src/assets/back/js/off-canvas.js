document.addEventListener("DOMContentLoaded", function() {
  const offcanvasButton = document.querySelector('[data-toggle="offcanvas"]');
  const sidebar = document.querySelector('.sidebar-offcanvas');

  offcanvasButton.addEventListener("click", function() {
    console.log("Button clicked");
    sidebar.classList.toggle('active');
  });
});
