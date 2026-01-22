<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page import="model.utente.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Ispeziona utente"/>
        <jsp:param name="styles" value="admin.css,showAll.css"/>
        <jsp:param name="scripts" value="admin.js"/>
    </jsp:include>
</head>

<body>
<%
    Utente utente = (Utente) request.getAttribute("utente");
%>

<main class="app">
    <section class="content grid-y">
        <%@include file="../../partials/admin/header.jsp"%>
        <%@include file="../../partials/admin/navbar.jsp"%>
        <div class="body grid-y justify-center align-center">
            <%
                if (utente.getFotoProfilo() == null) {
            %>
            <img src="${pageContext.request.contextPath}/images/no_avatar.jpg">
            <%
            } else {
            %>
            <img src="${pageContext.request.contextPath}/covers/<%=utente.getFotoProfilo()%>">
            <%
                }
            %>

            <table class="table">
                <thead>
                <tr>
                    <th>Nickname</th>
                    <th>Email</th>
                    <th>Admin</th>
                </tr>
                </thead>
                <tr>
                    <td data-head="Nickname"><%=utente.getNickname()%></td>
                    <td data-head="Email"><%=utente.getEmail()%></td>
                    <%
                        if (utente.getManager()) {
                    %>
                    <td data-head="Admin">Sì</td>
                    <%
                    } else {
                    %>
                    <td data-head="Admin">No</td>
                    <%
                        }
                    %>
                </tr>
            </table>

            <table class="table">
                <caption>Corsi creati</caption>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Prezzo</th>
                </tr>
                </thead>
                <%
                    for (Corso corso: utente.getCorsiCreati()) {
                %>
                <tr>
                    <td data-head="ID"> <%=corso.getId()%> </td>
                    <td data-head="Nome"> <%=corso.getNome()%> </td>
                    <td data-head="Prezzo"> <%=corso.getPrezzoBase()%>€ </td>
                </tr>
                <%
                    }
                %>
            </table>

            <table class="table">
                <caption>Corsi a cui ha partecipato</caption>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Prezzo</th>
                </tr>
                </thead>
                <%
                    for (Corso corso: utente.getCorsiPartecipati()) {
                %>
                <tr>
                    <td data-head="ID"> <%=corso.getId()%> </td>
                    <td data-head="Nome"> <%=corso.getNome()%> </td>
                    <td data-head="Prezzo"> <%=corso.getPrezzoBase()%>€ </td>
                </tr>
                <%
                    }
                %>
            </table>

            <table class="table data-table">
                <caption>Ordini effettuati</caption>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Data ordine</th>
                </tr>
                </thead>
                <%
                    for (Ordine ordine: utente.getOrdini()) {
                %>
                <tr>
                    <td data-head="ID"> <%=ordine.getId()%> </td>
                    <td data-head="Data"> <%=ordine.getData().toString()%> </td>
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
