<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Corsi a cui partecipi"/>
    </jsp:include>


    <style>
        table {
            margin-left: auto;
            margin-right: auto;
        }
        table, th, td {
            width: 50%;
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
    <%
        ArrayList<Corso> corsipartecipati = (ArrayList<Corso>) request.getAttribute("corsipartecipati");
    %>

    <table>
        <caption>Corsi Partecipati</caption>
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Prezzo</th>
        </tr>
        <%
            for (Corso corso: corsipartecipati) {
        %>
        <tr>
            <td> <%=corso.getId()%> </td>
            <td> <%=corso.getNome()%> </td>
            <td> <%=corso.getPrezzoBase()%> </td>
        </tr>
        <%
            }
        %>
    </table> <br>

    <a href="${pageContext.request.contextPath}/user/profile">Torna indietro</a>

</body>
</html>
