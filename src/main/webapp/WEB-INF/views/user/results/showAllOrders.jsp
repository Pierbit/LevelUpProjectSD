<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Ordini effettuati"/>
        <jsp:param name="styles" value="admin.css,showAll.css,user.css"/>
        <jsp:param name="scripts" value="home.js"/>
    </jsp:include>
</head>
<body onload="loadCategorie()">
<%
    ArrayList<Ordine> listaordini = (ArrayList<Ordine>) request.getAttribute("ordinieffettuati");
%>
<main class="app">
    <section class="content grid-y">
        <%@include file="/WEB-INF/views/partials/home/header.jsp"%>
        <%@include file="/WEB-INF/views/partials/home/categoriebar.jsp"%>
        <div class="body grid-y justify-center align-center">
            <table class="table data-table">
                <caption>Ordini effettuati</caption>
                <tr>
                    <th>ID</th>
                    <th>Data</th>
                    <th>Ispeziona</th>
                    <th>Elimina</th>
                </tr>
                <%
                    for (Ordine ordine: listaordini) {
                %>
                <tr>
                    <td data-head="ID"><%=ordine.getId()%>
                    </td>
                    <td data-head="Data"><%=ordine.getData()%>
                    </td>
                    <td data-head="Ispeziona">
                        <form action="${pageContext.request.contextPath}/user/inspectOrdineEffettuato" method="post">
                            <input type="hidden" name="ordineid" value="<%=ordine.getId()%>">
                            <input type="submit" value="Ispeziona">
                        </form>
                    </td>
                    <td data-head="Elimina">
                        <form action="${pageContext.request.contextPath}/user/deleteOrdine" method="post">
                            <input type="hidden" name="ordineid" value="<%=ordine.getId()%>">
                            <input type="submit" value="Elimina">
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <jsp:include page="/WEB-INF/views/partials/paginator.jsp">
                <jsp:param name="resource" value="${pageContext.request.contextPath}/user/showAllOrders"/>
            </jsp:include>
        </div>
        <%@include file="/WEB-INF/views/partials/home/footer.jsp"%>
    </section>
</main>
</body>
</html>