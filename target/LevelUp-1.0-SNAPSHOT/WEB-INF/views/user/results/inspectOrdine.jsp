<%@ page import="model.utente.Utente" %>
<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Ispeziona corso"/>
        <jsp:param name="styles" value="admin.css,showAll.css,user.css"/>
        <jsp:param name="scripts" value="home.js"/>
    </jsp:include>

    <style>
        .box {
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            width: 90%;
            background-color: white;
        }

        .box .container {
            padding: 2px 16px;
            color: white;
            margin-bottom: 1rem;
        }

        .card {
            margin: .5rem;
        }

        @media only screen and (min-width: 769px) {
            .box {
                width: 70%;
            }
        }
    </style>
</head>
<body onload="loadCategorie()">

<%
    ArrayList<Corso> corsi = (ArrayList<Corso>) request.getAttribute("corsiassociati");
    Double spesatotale = (Double) request.getAttribute("spesatotale");
%>

<main class="app">
    <section class="content grid-y">
        <%@include file="/WEB-INF/views/partials/home/header.jsp"%>
        <%@include file="/WEB-INF/views/partials/home/categoriebar.jsp"%>
        <div class="body grid-y align-center">
            <div class="box">
                <h2 style="text-align: center">Spesa totale: <%=spesatotale%>€</h2>
                <div class="container">
                    <div class="grid-x justify-center">
                        <% for (Corso corso: corsi) { %>
                            <jsp:include page="../../partials/cards/corso.jsp">
                                <jsp:param name="id" value="<%=corso.getId()%>"/>
                                <jsp:param name="cover" value="<%=corso.getCopertina()%>"/>
                                <jsp:param name="title" value="<%=corso.getNome()%>"/>
                                <jsp:param name="user" value=""/>
                                <jsp:param name="price" value="<%=corso.getPrezzoBase()%>"/>
                            </jsp:include>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="/WEB-INF/views/partials/home/footer.jsp"%>
    </section>
</main>
</body>
</html>
