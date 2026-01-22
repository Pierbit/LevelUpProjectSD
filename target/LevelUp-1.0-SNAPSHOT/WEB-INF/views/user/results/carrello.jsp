<%@ page import="model.carrello.Carrello" %>
<%@ page import="model.oggetto.Oggetto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <%
            Carrello carrello = (Carrello) request.getAttribute("carrello");
            if (carrello != null) {
                for (Oggetto oggetto: carrello.getOggetti()) {
        %>
                    <p><%=oggetto.getId()%></p>
        <%
                }
            }
        %>
    </body>
</html>
