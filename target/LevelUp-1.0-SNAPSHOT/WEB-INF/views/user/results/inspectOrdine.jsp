<%@ page import="model.utente.Utente" %>
<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Ispeziona corso"/>
        <jsp:param name="styles" value="admin.css"/>
    </jsp:include>

    <style>
        .bgr {
            background: linear-gradient(var(--shade5), var(--shade1));
        }

        .mainpage {
            padding: 1rem;
            background-color: white;
            border-radius: 5px;
        }

        .mainpage > * {
            margin: 3px;
        }

        .test {
            width: 80vw;
            height: 80vh;
            background-color: lightgrey;
            max-width: 100%;
        }

        h4 {
            margin: 0;
            padding: 1rem;
            color: white;
            background-color: var(--shade3);
            border-radius: 5px 5px 0 0;
        }

        h2 {
            justify-self: center;
            font-size: 3rem;
        }

    </style>
</head>
<body class="bgr grid-y justify-center align-center">

<%
    ArrayList<Corso> corsi = (ArrayList<Corso>) request.getAttribute("corsiassociati");
    Double spesatotale = (Double) request.getAttribute("spesatotale");
%>

<%@include file="/WEB-INF/views/partials/user/header.jsp" %>

<div class="test grid-x justify-center align-center mainpage w80">
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

    <jsp:include page="/WEB-INF/views/partials/admin/statscard.jsp">
        <jsp:param name="title" value="Spesa totale"/>
        <jsp:param name="stat" value="<%=spesatotale%>"/>
    </jsp:include>

</div>

<jsp:include page="/WEB-INF/views/partials/user/footer.jsp"></jsp:include>

</body>
</html>
