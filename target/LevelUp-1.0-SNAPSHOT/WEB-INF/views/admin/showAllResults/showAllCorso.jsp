<%@ page import="java.util.List" %>
<%@ page import="model.corso.Corso" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="../../partials/head.jsp">
            <jsp:param name="title" value="Elenco dei corsi"/>
            <jsp:param name="styles" value="admin.css,showAll.css"/>
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
                        List<Corso> corsi = (List<Corso>) request.getAttribute("elencoCorsi");
                    %>

                    <table class="table">
                        <caption>Elenco corsi</caption>
                        <tr>
                            <th>Id</th>
                            <th>Nome</th>
                            <th>Ispeziona</th>
                            <th>Aggiorna</th>
                            <th>Associa</th>
                            <th>Elimina</th>
                        </tr>
                        <%
                            for (Corso corso: corsi) {
                        %>
                        <tr>
                            <td data-head="Id"> <%=corso.getId()%> </td>
                            <td data-head="Nome"> <%=corso.getNome()%> </td>
                            <td data-head="Ispeziona">
                                <form action="${pageContext.request.contextPath}/manager/inspectCorso" method="post">
                                    <input type="hidden" name="corsoid" value="<%=corso.getId()%>">
                                    <input type="submit" value="Ispeziona">
                                </form>
                            </td>
                            <td data-head="Aggiorna">
                                <form action="${pageContext.request.contextPath}/manager/updateCorso" method="post">
                                    <input type="hidden" name="corsoid" value="<%=corso.getId()%>">
                                    <input type="submit" value="Aggiorna">
                                </form>
                            </td>
                            <td data-head="Associa">
                                <form action="${pageContext.request.contextPath}/manager/associaCorso" method="post">
                                    <input type="hidden" name="corsoid" value="<%=corso.getId()%>">
                                    <input type="submit" value="Associa">
                                </form>
                            </td>
                            <td data-head="Elimina">
                                <form action="${pageContext.request.contextPath}/manager/deleteCorso" method="post">
                                    <input type="hidden" name="corsoid" value="<%=corso.getId()%>">
                                    <input type="submit" value="Elimina">
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>

                    <a href="${pageContext.request.contextPath}/manager/createCorso">
                        Inserisci un nuovo corso.
                    </a>

                    <jsp:include page="/WEB-INF/views/partials/paginator.jsp">
                        <jsp:param name="resource" value="${pageContext.request.contextPath}/manager/showAllCorso"/>
                    </jsp:include>
                </div>
            </section>
        </main>
    </body>
</html>
