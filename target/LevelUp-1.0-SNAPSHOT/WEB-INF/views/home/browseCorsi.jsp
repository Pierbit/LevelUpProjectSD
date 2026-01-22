<%@ page import="java.util.List" %>
<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.tag.Tag" %>
<%@ page import="model.categoria.Categoria" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>

    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Elenco dei corsi"/>
        <jsp:param name="styles" value="user.css,admin.css"/>
        <jsp:param name="scripts" value="home.js"/>
    </jsp:include>

</head>

<body class="home grid-y" onload="loadCategorie()">
<%
    ArrayList<Tag> tags = (ArrayList<Tag>) request.getAttribute("tags");
    String categoriacercata = (String) request.getAttribute("categoriacercata");
    ArrayList<Corso> corsicercati = (ArrayList<Corso>) request.getAttribute("corsicercati");
%>

<%@include file="/WEB-INF/views/partials/home/header.jsp" %>
<%@include file="/WEB-INF/views/partials/home/categoriebar.jsp" %>

<div class="grid-x align-center justify-center">
    <fieldset class="grid-y align-center justify-center">
        <form action="${pageContext.request.contextPath}/home/browseFilteredCorsi">
            <label for="creatorName">Nome content creator</label><br>
            <input type="text" id="creatorName" name="creatorName" placeholder="Content creator"> <br>
            <label for="corsoName">Nome del corso</label><br>
            <input type="text" id="corsoName" name="corsoName" placeholder="Nome corso"><br>
            <label for="minPrice">Prezzo</label><br>
            <input type="number" id="minPrice" name="minPrice" placeholder="min">
            <input type="number" id="maxPrice" name="maxPrice" placeholder="max"><br>
            <label for="tags">Tags</label><br>
            <select name="tags" id="tags" multiple><br>
                <%
                    for (Tag tag : tags) {
                %>
                <option value="<%=tag.getNome()%>"><%=tag.getNome()%>
                </option>
                <%
                    }
                %>
            </select> <br>
            <button type="submit" class="btn primary">Associa</button>
        </form>
    </fieldset>
    <fieldset class="grid-y align-center justify-center">
        <table class="table">
            <caption>Elenco corsi</caption>
            <tr>
                <th>Nome</th>
                <th>Prezzo</th>
                <th>Content creator</th>
            </tr>
            <%
                for (Corso corso : corsicercati) {
            %>
            <tr>
                <td data-head="Nome"><%=corso.getNome()%>
                </td>
                <td data-head="Prezzo"><%=corso.getPrezzoBase()%>
                </td>
                <td data-head="Content creator"><%=corso.getUtenteCreatore().getNickname()%>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </fieldset>
</div>

<input type="hidden" id="categoriaName" name="categoriaName" value="<%=categoriacercata%>">
<%@include file="/WEB-INF/views/partials/home/footer.jsp" %>

</body>
</html>
