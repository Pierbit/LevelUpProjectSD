<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Corsi sottoscritti"/>
        <jsp:param name="styles" value="admin.css,showAll.css"/>
    </jsp:include>
</head>
<body>
<%
    ArrayList<Corso> corsipartecipati = (ArrayList<Corso>) request.getAttribute("corsipartecipati");
%>
<main class="app">
    <section class="content grid-y">
        <%@include file="../../partials/user/header.jsp" %>
        <div class="body grid-y justify-center align-center">
            <table class="table data-table">
                <caption>Corsi a cui partecipi</caption>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Prezzo</th>
                    <th>Disiscrizione</th>
                </tr>
                <%
                    for (Corso corso : corsipartecipati) {
                %>
                <tr>
                    <td data-head="Id"><%=corso.getId()%>
                    </td>
                    <td data-head="Nome"><%=corso.getNome()%>
                    </td>
                    <td data-head="Prezzo"><%=corso.getPrezzoBase()%>
                    <td data-head="Disiscrizione">
                        <form action="${pageContext.request.contextPath}/user/unsub" method="post">
                            <input type="hidden" name="corsoid" value="<%=corso.getId()%>">
                            <input type="submit" value="Disiscriviti">
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>

            <jsp:include page="/WEB-INF/views/partials/paginator.jsp">
                <jsp:param name="resource" value="${pageContext.request.contextPath}/user/showAllSubscribed"/>
            </jsp:include>
        </div>
    </section>
</main>
</body>
</html>