<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
    <head>
        <head>
            <jsp:include page="../../partials/head.jsp">
                <jsp:param name="title" value="Ispeziona tag"/>
            </jsp:include>

            <style>
                table {
                    width: 50%;
                    margin-left: auto;
                    margin-right: auto;
                }

                table, th, td {
                    border: 1px solid black;
                    border-collapse: collapse;
                }
            </style>
    </head>

    <body>
        <%
            ArrayList<Corso> corsiassociati = (ArrayList<Corso>) request.getAttribute("corsiassociati");
        %>

        <h2 style="text-align: center">${tagname}</h2>

        <table>
            <caption>Corsi associati</caption>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Prezzo</th>
            </tr>
            <%
                for (Corso corso: corsiassociati) {
            %>
            <tr>
                <td> <%=corso.getId()%> </td>
                <td> <%=corso.getNome()%> </td>
                <td> <%=corso.getPrezzoBase()%> </td>
            </tr>
            <%
                }
            %>
        </table>

        <p style="text-align: center">
            <a href="${pageContext.request.contextPath}/manager/showAllTag">Torna indietro</a>
        </p>
    </body>
</html>
