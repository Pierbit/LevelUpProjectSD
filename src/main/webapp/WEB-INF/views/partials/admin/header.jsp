<header class="topbar grid-x align-center">
    <span class="home-button" style="cursor:pointer;">
        <a href="${pageContext.request.contextPath}/">
            <%@include file="../../../../icons/home.svg"%>
        </a>
    </span>
    
    <span class="sidebar-switch" style="cursor:pointer;">
        <%@ include file="../../../../icons/th-menu.svg" %>
    </span>

    <label class="field command">
        <input type="text" id="adminsearchbar" name="adminsearchbar" form="searchbar" list="listacomandi" autocomplete="off" placeholder="Cerca">
        <datalist id="listacomandi">
            <option value="Mostra tutti gli utenti"></option>
            <option value="Mostra tutti i corsi"></option>
            <option value="Mostra tutti i tag"></option>
            <option value="Mostra tutte le categorie"></option>
            <option value="Mostra tutti gli ordini"></option>
            <option value="Crea una categoria"></option>
            <option value="Crea un corso"></option>
            <option value="Crea un tag"></option>
            <option value="Crea un utente"></option>
        </datalist>
        <button type="submit" form="searchbar" class="btn primary">Vai</button>

        <form id="searchbar" action="${pageContext.request.contextPath}/manager/executeSearchBar" class="hide">
        </form>
    </label>
</header>