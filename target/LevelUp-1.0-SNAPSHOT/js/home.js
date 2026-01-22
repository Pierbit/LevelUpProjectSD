function loadCategorie() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === XMLHttpRequest.DONE) {
            if (xhttp.status >= 100 && xhttp.status < 400) {

                var lista = JSON.parse(xhttp.responseText);

                for (let i = 0; i < Object.keys(lista.categorie).length; i++) {

                    let link = document.createElement("a");
                    link.innerHTML = lista.categorie[i].nome;
                    link.href = "/LevelUp_war_exploded/home/browseCorsi?categoria="+lista.categorie[i].nome;
                    link.className = "generatedlink";
                    document.getElementById("categoriebar").appendChild(link);
                }

            } else {
                const defaultcategorie = ["lavoro", "tecnologia", "natura"];
                for (let i = 0; i < Object.keys(defaultcategorie).length; i++) {
                    let link = document.createElement("a");
                    link.innerHTML = defaultcategorie[i];
                    link.href = "/LevelUp_war_exploded/home/browseCorsi?categoria="+defaultcategorie[i];
                    link.className = "generatedlink";
                    document.getElementById("categoriebar").appendChild(link);
                }
            }
        }
    }
    xhttp.open("GET", "/LevelUp_war_exploded/home/loadCategorie", true);
    xhttp.send();
}

hamburger = document.getElementsByClassName("sidebar-switch")[0];
hamburger.addEventListener('click', function () {

    const navbar = document.getElementsByClassName("navbar")[0];
    navbar.classList.toggle("hide");

})