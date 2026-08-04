<%@ page import="model.categoria.Categoria" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.corso.Corso" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Carrello"/>
        <jsp:param name="styles" value="user.css"/>
        <jsp:param name="scripts" value="home.js"/>
    </jsp:include>

    <style>
        .courses {
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            background-color: white;
            width: 90%;
            padding: .5rem 1rem 2rem;
            color: black;
        }

        .courses > h2 {
            text-align: center;
        }

        .courses > div > * {
            margin: .5rem;
        }
    </style>
</head>

<body onload="loadCategorie()">
<%
    ArrayList<Corso> corsi = (ArrayList<Corso>) request.getAttribute("oggettinelcarrello");
%>

<main class="app">
    <section class="content grid-y">
        <%@include file="../../partials/home/header.jsp"%>
        <%@include file="../../partials/home/categoriebar.jsp"%>

        <div class="body grid-y justify-center align-center">
            <div class="grid-y courses justify-center align-center">
                <h2>Carrello</h2>

                <div class="grid-x align-center justify-center">
                    <% if (corsi.isEmpty()) { %>
                        <h4>Il carrello è vuoto. Che ne dici di iniziare a esplorare i corsi a disposizone?</h4>
                    <% } else { %>
                        <% for (Corso corso: corsi) { %>
                        <jsp:include page="/WEB-INF/views/partials/cards/corso.jsp">
                            <jsp:param name="id" value="<%=corso.getId()%>"/>
                            <jsp:param name="cover" value="<%=corso.getCopertina()%>"/>
                            <jsp:param name="title" value="<%=corso.getNome()%>"/>
                            <jsp:param name="user" value="<%=corso.getUtenteCreatore().getNickname()%>"/>
                            <jsp:param name="price" value="<%=corso.getPrezzoBase()%>"/>
                        </jsp:include>
                        <% } %>
                    <% } %>
                </div>

                <% if (!corsi.isEmpty()) { %>
                    <% if (session.getAttribute("utente") != null) { %>
                    <form action="${pageContext.request.contextPath}/home/acquistaCarrello" method="post">
                        <button type="submit" class="btn primary">Completa ordine</button>
                    </form>
                    <% } else { %>
                    <form action="${pageContext.request.contextPath}/accounts/register" method="get">
                        <button type="submit" class="btn primary">Completa ordine</button>
                    </form>
                    <% } %>
                <% } %>
            </div>


        </div>

        <%@include file="../../partials/home/footer.jsp"%>
    </section>
</main>
</body>
</html>