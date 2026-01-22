<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page import="model.utente.Utente" %>
<%@ page import="model.tag.Tag" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="../../partials/head.jsp">
            <jsp:param name="title" value="Ispeziona corso"/>
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

        <h2 style="text-align: center"> ${nomecategoria} </h2>

        <table>
            <caption>Corsi associati</caption>
            <tr>
                <th>Id</th>
                <th>Nome</th>
                <th>Prezzo base</th>
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
            <a href="${pageContext.request.contextPath}/manager/showAllCategoria">Torna indietro</a>
        </p>
    </body>
</html>
