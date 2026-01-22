<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Corsi Pubblicati"/>
        <jsp:param name="styles" value="admin.css,showAll.css"/>
    </jsp:include>
</head>
<body>
<%
    ArrayList<Corso> corsipubblicati = (ArrayList<Corso>) request.getAttribute("corsipubblicati");
%>
<main class="app">
    <section class="content grid-y">
        <%@include file="../../partials/user/header.jsp" %>
        <div class="body grid-y justify-center align-center">
            <table class="table data-table">
                <caption>Corsi Pubblicati</caption>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Prezzo</th>
                    <th>Ispeziona</th>
                    <th>Aggiorna</th>
                    <th>Elimina</th>
                </tr>
                <%
                    for (Corso corso : corsipubblicati) {
                %>
                <tr>
                    <td data-head="Id"><%=corso.getId()%>
                    </td>
                    <td data-head="Nome"><%=corso.getNome()%>
                    </td>
                    <td data-head="Prezzo"><%=corso.getPrezzoBase()%>
                    </td>
                    <td data-head="Ispeziona">
                        <form action="${pageContext.request.contextPath}/user/inspectCorsoPubblicato" method="post">
                            <input type="hidden" name="corsoid" value="<%=corso.getId()%>">
                            <input type="submit" value="Ispeziona">
                        </form>
                    </td>
                    <td data-head="Aggiorna">
                        <form action="${pageContext.request.contextPath}/user/updateCorso" method="post">
                            <input type="hidden" name="corsoid" value="<%=corso.getId()%>">
                            <input type="submit" value="Aggiorna">
                        </form>
                    </td>
                    <td data-head="Elimina">
                        <form action="${pageContext.request.contextPath}/user/deleteCorso" method="post">
                            <input type="hidden" name="corsoid" value="<%=corso.getId()%>">
                            <input type="submit" value="Elimina">
                        </form>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
            <jsp:include page="/WEB-INF/views/partials/paginator.jsp">
                <jsp:param name="resource" value="${pageContext.request.contextPath}/user/showAllPublished"/>
            </jsp:include>
        </div>
    </section>
</main>
</body>
</html>