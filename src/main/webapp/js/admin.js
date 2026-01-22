/*const hamburger = document.getElementsByClassName("sidebar-switch")[0];
hamburger.addEventListener('click', function () {
    const sidebar = document.getElementsByClassName("sidebar")[0];
    const content = document.getElementsByClassName("content")[0];
    sidebar.classList.toggle("collapse");
    content.classList.toggle("full-width");
})*/

hamburger = document.getElementsByClassName("sidebar-switch")[0];
hamburger.addEventListener('click', function () {
    const navbar = document.getElementsByClassName("navbar")[0];
    //const content = document.getElementsByClassName("content")[0];
    navbar.classList.toggle("hide");
    //content.classList.toggle("full-width");
})