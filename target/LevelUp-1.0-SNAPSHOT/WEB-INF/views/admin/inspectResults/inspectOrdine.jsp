<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.List" %>
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
    ArrayList<Corso> corsi = (ArrayList<Corso>) request.getAttribute("corsiassociati");
%>
<main class="app">
    <section class="content grid-y">
        <%@include file="../../partials/admin/header.jsp" %>
        <%@include file="../../partials/admin/navbar.jsp" %>
        <div class="body grid-y justify-center align-center">
            <h2 style="text-align: center">Ordine</h2>
            <table class="table">
                <caption>Utente associato all'ordine</caption>
                <tr>
                    <th>Nickname</th>
                    <th>Email</th>
                </tr>
                <tr>
                    <td> ${utente.nickname} </td>
                    <td> ${utente.email} </td>
                </tr>

            </table>
            <table class="table">
                <caption>Corsi associati all'ordine</caption>
                <tr>
                    <th>Id</th>
                    <th>Nome</th>
                    <th>Prezzo</th>
                </tr>
                <%
                    for (Corso corso: corsi) {
                %>
                <tr>
                    <td data-head="Id"><%=corso.getId()%>
                    </td>
                    <td data-head="Nome"><%=corso.getNome()%>
                    </td>
                    <td data-head="prezzoBase"><%=corso.getPrezzoBase()%>
                    </td>
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
