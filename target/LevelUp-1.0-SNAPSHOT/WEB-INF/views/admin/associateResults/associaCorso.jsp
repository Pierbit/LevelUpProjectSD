<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.categoria.Categoria" %>
<%@ page import="model.tag.Tag" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Associa un corso"/>
        <jsp:param name="styles" value="admin.css,showAll.css"/>
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

        <div class="body grid-x justify-center">
            <form action="${pageContext.request.contextPath}/manager/executeAssociaCorso"
                  method="post" class="app justify-center align-center">
                <fieldset class="grid-y cell w50 create">
                    <h2 id="login" style="text-align: center">Associa un corso</h2>
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
                    <label for="tags">Scegli i tag da associare</label>
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
                </fieldset>
            </form>
        </div>

    </section>
</main>

</body>
</html>
