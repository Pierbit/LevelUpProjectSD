<%@ page import="model.categoria.Categoria" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.corso.Corso" %>
<%@ page import="model.carrello.Carrello" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Visualizza corso"/>
        <jsp:param name="styles" value="user.css"/>
        <jsp:param name="scripts" value="home.js"/>
    </jsp:include>

    <style>
        .box {
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
            width: 95%;
            background-color: white;
        }

        .box > .cover {
            width: 100%;
        }

        .box .container {
            padding: 2px 16px;
            color: black;
        }

        .container > .title, .container > .creator {
            text-align: center;
        }

        .container > .text {
            text-align: justify;
        }

        @media only screen and (min-width: 769px) {
            .box {
                width: 70%;
            }
        }
    </style>
</head>

<body onload="loadCategorie()">
<% Corso corso = (Corso) request.getAttribute("corso"); %>
<main class="app">
    <section class="content grid-y">
        <%@include file="../partials/home/header.jsp" %>
        <%@include file="../partials/home/categoriebar.jsp" %>

        <div class="body grid-y justify-center align-center">
            <div class="box">
                <img src="${pageContext.request.contextPath}/covers/<%=corso.getCopertina()%>" class="cover">
                <div class="container">
                    <h1 class="title"><%=corso.getNome()%>
                    </h1>
                    <h5 class="creator">di
                        <a href="${pageContext.request.contextPath}/user/profile?nickname=<%=corso.getUtenteCreatore().getNickname()%>">
                            <%=corso.getUtenteCreatore().getNickname()%>
                        </a>
                    </h5>
                    <% if ((boolean) request.getAttribute("token")) { %>
                    <p class="text"><%=corso.getTesto()%>
                    </p>
                    <% } else { %>
                    <h1 style="text-align: center">Non hai ancora comprato questo corso</h1>
                    <%
                        if (!((boolean) request.getAttribute("contains"))) { //Se il carrello NON contiene il corso...
                    %>
                            <div class="grid-y align-center" style="margin-bottom: 1rem">
                                <form action="${pageContext.request.contextPath}/home/aggiungiAlCarrello" method="post">
                                    <input type="hidden" id="corsoid" name="corsoid" value="<%=corso.getId()%>">
                                    <button type="submit" class="btn primary">Aggiungi al carrello</button>
                                </form>
                            </div>
                        <% } else { //Se invece il carrello contiene il corso... %>
                            <div class="grid-y align-center" style="margin-bottom: 1rem">
                                <form action="${pageContext.request.contextPath}/home/rimuoviDalCarrello" method="post">
                                    <input type="hidden" id="corsoid1" name="corsoid" value="<%=corso.getId()%>">
                                    <button type="submit" class="btn primary">Rimuovi dal carrello</button>
                                </form>
                            </div>
                        <% } %>
                    <% } %>
                </div>
            </div>
        </div>

        <%@include file="../partials/home/footer.jsp" %>
    </section>
</main>
</body>
</html>