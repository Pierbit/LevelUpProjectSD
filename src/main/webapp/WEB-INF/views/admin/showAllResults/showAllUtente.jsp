<%@ page import="java.util.List" %>
<%@ page import="model.utente.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="../../partials/head.jsp">
            <jsp:param name="title" value="Elenco degli utenti"/>
            <jsp:param name="styles" value="user.css,admin.css,showAll.css"/>
            <jsp:param name="scripts" value="admin.js"/>
        </jsp:include>
    </head>

    <body>
        <main class="app">
            <section class="content grid-y">
                <%@include file="../../partials/admin/header.jsp"%>

                <%@include file="../../partials/admin/navbar.jsp"%>

                <div class="body grid-y justify-center align-center">
                    <%
                        List<Utente> utenti = (List<Utente>) request.getAttribute("elencoUtenti");
                    %>

                    <table class="table">
                        <caption>Elenco utenti</caption>
                        <tr>
                            <th>Nickname</th>
                            <th>Email</th>
                            <th>Ispeziona</th>
                            <th>Elimina</th>
                        </tr>
                        <%
                            for (Utente utente: utenti) {
                        %>
                        <tr>
                            <td data-head="Nickname"> <%=utente.getNickname()%> </td>
                            <td data-head="Email"> <%=utente.getEmail()%> </td>
                            <td data-head="Ispeziona">
                                <form action="${pageContext.request.contextPath}/manager/inspectUser"
                                      method="post">
                                    <input type="hidden" name="username" value="<%=utente.getNickname()%>">
                                    <input type="submit" value="Ispeziona">
                                </form>
                            </td>
                            <td data-head="Elimina">
                                <form action="${pageContext.request.contextPath}/manager/deleteUtente" method="post">
                                    <input type="hidden" name="username" value="<%=utente.getNickname()%>">
                                    <input type="submit" value="Elimina">
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>

                    <jsp:include page="/WEB-INF/views/partials/paginator.jsp">
                        <jsp:param name="resource" value="${pageContext.request.contextPath}/manager/showAllUtente"/>
                    </jsp:include>
                </div>
            </section>
        </main>
    </body>
</html>
