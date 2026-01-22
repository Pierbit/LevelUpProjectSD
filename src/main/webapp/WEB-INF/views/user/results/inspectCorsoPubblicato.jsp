<%@ page import="model.utente.Utente" %>
<%@ page import="model.corso.Corso" %>
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
    Corso corso = (Corso) request.getAttribute("corso");
    Double guadagno = (Double) request.getAttribute("guadagno");
    int numiscritti = (int) request.getAttribute("numiscritti");
%>

<%@include file="/WEB-INF/views/partials/user/header.jsp" %>

<div class="test grid-y justify-center align-center mainpage w80">

        <%
        if ((corso.getCopertina()) == null) {
    %>
    <img src="${pageContext.request.contextPath}/images/no_avatar.jpg">
        <%
        } else {
    %>
    <img src="${pageContext.request.contextPath}/covers/<%=corso.getCopertina()%>">
        <%
        }
    %>
    <textarea id="descrizione" name="descrizione" rows="4" cols="50"
              contenteditable="false"><%=corso.getTesto()%></textarea>

    <jsp:include page="/WEB-INF/views/partials/admin/statscard.jsp">
        <jsp:param name="title" value="Guadagno totale"/>
        <jsp:param name="stat" value="<%=guadagno%>"/>
    </jsp:include>

    <jsp:include page="/WEB-INF/views/partials/admin/statscard.jsp">
        <jsp:param name="title" value="Partecipanti totali"/>
        <jsp:param name="stat" value="<%=numiscritti%>"/>
    </jsp:include>
</div>
    <jsp:include page="/WEB-INF/views/partials/user/footer.jsp"></jsp:include>
</body>
</html>
