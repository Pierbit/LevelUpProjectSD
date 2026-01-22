<%@ page import="model.corso.Corso" %>
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
            Corso corso = (Corso) request.getAttribute("corso");
        %>

        <main class="app">
            <section class="content grid-y">
                <%@include file="../../partials/admin/header.jsp"%>
                <%@include file="../../partials/admin/navbar.jsp"%>
                <div class="body grid-y justify-center align-center">
                    <%
                        if (corso.getCopertina() == null) {
                    %>
                        <img src="${pageContext.request.contextPath}/images/no_avatar.jpg">
                    <%
                        } else {
                    %>
                        <img src="${pageContext.request.contextPath}/covers/<%=corso.getCopertina()%>" style="max-width: 90%">
                    <%
                        }
                    %>

                    <table class="table">
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Prezzo Base</th>
                        </tr>
                        <tr>
                            <td><%=corso.getId()%></td>
                            <td><%=corso.getNome()%></td>
                            <td><%=corso.getPrezzoBase()%>€</td>
                        </tr>
                    </table>

                    <fieldset style="background-color: white; border: 1px solid black; width: 75%"><%=corso.getTesto()%></fieldset>

                    <table class="table">
                        <caption>Utenti partecipanti</caption>
                        <tr>
                            <th>Nickname</th>
                            <th>Email</th>
                        </tr>
                        <%
                            for (Utente utente: corso.getUtentiPartecipanti()) {
                        %>
                        <tr>
                            <td> <%=utente.getNickname()%> </td>
                            <td> <%=utente.getEmail()%> </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>

                    <table class="table data-table">
                        <caption>Utente creatore del corso</caption>
                        <tr>
                            <th>Nickname</th>
                            <th>Email</th>
                        </tr>
                        <tr>
                            <td> <%=corso.getUtenteCreatore().getNickname()%> </td>
                            <td> <%=corso.getUtenteCreatore().getEmail()%> </td>
                        </tr>
                    </table>

                    <table class="table data-table">
                        <caption>Categoria associata al corso</caption>
                        <tr>
                            <th>Nome</th>
                        </tr>
                        <tr>
                            <td> <%=corso.getCategoria().getNome()%> </td>
                        </tr>
                    </table>

                    <table class="table data-table">
                        <caption>Tag associati al corso</caption>
                        <tr>
                            <th>Nome tag</th>
                        </tr>
                        <%
                            for (Tag tag: corso.getTags()) {
                        %>
                        <tr>
                            <td> <%=tag.getNome()%> </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                </div>
            </section>
        </main>
    </body>
</html>
