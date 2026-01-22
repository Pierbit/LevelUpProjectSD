<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page import="model.utente.Utente" %>
<%@ page import="model.tag.Tag" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="../../partials/head.jsp">
            <jsp:param name="title" value="Ispeziona corso"/>
            <jsp:param name="styles" value="user.css,admin.css,showAll.css"/>
            <jsp:param name="scripts" value="admin.js"/>
        </jsp:include>
    </head>

    <body>
        <%
            ArrayList<Corso> corsiassociati = (ArrayList<Corso>) request.getAttribute("corsiassociati");
        %>

        <main class="app">
            <section class="content grid-y">
                <%@include file="../../partials/admin/header.jsp"%>

                <%@include file="../../partials/admin/navbar.jsp"%>

                <div class="body grid-y justify-center align-center">

                    <table class="table">
                        <caption>Elenco corsi associati</caption>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                        </tr>
                        <%
                            for (Corso c: corsiassociati) {
                        %>
                        <tr>
                            <td data-head="Id"> <%=c.getId()%></td>
                            <td data-head="Nome"> <%=c.getNome()%> </td>

                        </tr>
                        <%
                            }
                        %>
                    </table>

                    <a href="${pageContext.request.contextPath}/manager/showAllCategoria">
                        Torna indietro
                    </a>
                </div>
            </section>
        </main>
    </body>
</html>
