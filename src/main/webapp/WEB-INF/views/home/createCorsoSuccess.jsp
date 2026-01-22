<%--
  Created by IntelliJ IDEA.
  User: ppcam
  Date: 26/07/2021
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Crea un corso"/>
        <jsp:param name="styles" value="user.css"/>
        <jsp:param name="scripts" value="home.js,admin.js,validators.js"/>
    </jsp:include>

    <style>
        .logging {
            margin-bottom: 1rem;
        }

        .logging > * {
            margin: .5rem;
        }

        .box {
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            width: 90%;
            background-color: white;
        }

        .box .container {
            padding: 2px 16px;
            color: black;
        }

        h3{
            font-style: italic;
        }

        h4{
            font-style: italic;
        }

        @media only screen and (min-width: 769px) {
            .box {
                width: 70%;
            }
        }
    </style>
</head>
<body onload="loadCategorie()">
<main class="app">
    <section class="content grid-y">
        <%@include file="../partials/home/header.jsp"%>
        <%@include file="../partials/home/categoriebar.jsp"%>

        <div class="body grid-y align-center">
            <div class="box">
                <div class="container">
                    <h2 style="text-align: center">Il corso è stato registrato con successo!</h2> <br>
                    <h3 style="text-align: center">In seguito all'approvazione dell'amministratore il corso comprarirà nella sezione <span style="color: #5B00B5">Esplora</span></h3>
                    <h4 style="text-align: center">Puoi visualizzare i corsi che hai pubblicato sul profilo personale</h4>
                    <form action="${pageContext.request.contextPath}">
                        <button class="btn primary">Torna alla home</button>
                    </form>
                </div>
            </div>
        </div>

        <%@include file="../partials/home/footer.jsp"%>
    </section>
</main>
</body>
</html>
