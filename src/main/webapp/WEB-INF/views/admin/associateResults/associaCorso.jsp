<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.categoria.Categoria" %>
<%@ page import="model.tag.Tag" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Associa un corso"/>
        <jsp:param name="styles" value="user.css,admin.css,showAll.css"/>
        <jsp:param name="scripts" value="admin.js,validators.js"/>
    </jsp:include>
</head>
<body>

<%
    Corso corso = (Corso) request.getAttribute("corso");
    ArrayList<Categoria> categorie = (ArrayList<Categoria>) request.getAttribute("categorie");
    ArrayList<Tag> tags = (ArrayList<Tag>) request.getAttribute("tags");
%>

<main class="app">
    <section class="content grid-y">
        <%@include file="../../partials/admin/header.jsp"%>

        <%@include file="../../partials/admin/navbar.jsp"%>

        <div class="body grid-y align-center">
            <div class="box">
                <div class="container">
                    <form action="${pageContext.request.contextPath}/manager/executeAssociaCorso"
                          method="post" class="grid-x justify-center">
                        <h2 style="text-align: center">Associa un corso</h2>
                        <div class="grid-y cell w80 logging">
                            <label for="categoria">Scegli la categoria del corso</label>
                            <input type="text" id="categoria" name="categoria" list="listacategorie" required> <br> <br>
                            <datalist id="listacategorie">
                                <%
                                    for(Categoria categoria: categorie) {
                                %>
                                <option value="<%=categoria.getNome()%>"><%=categoria.getNome()%></option>
                                <%
                                    }
                                %>
                            </datalist>
                            <label for="tags" class="field">Scegli i tag da associare</label>
                            <select name="tags" id="tags" multiple required>
                                <%
                                    for(Tag tag: tags) {
                                %>
                                <option value="<%=tag.getNome()%>"><%=tag.getNome()%></option>
                                <%
                                    }
                                %>
                            </select> <br>
                            <input type="hidden" id="corsoid" name="corsoid" value="<%=corso.getId()%>">
                            <button type="submit" class="btn primary">Associa</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </section>
</main>

</body>
</html>
