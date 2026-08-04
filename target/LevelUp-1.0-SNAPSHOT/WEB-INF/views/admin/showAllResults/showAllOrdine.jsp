<%@ page import="java.util.List" %>
<%@ page import="model.ordine.Ordine" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="../../partials/head.jsp">
            <jsp:param name="title" value="Elenco degli ordini"/>
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
                        List<Ordine> ordini = (List<Ordine>) request.getAttribute("elencoOrdini");
                    %>

                    <table class="table">
                        <caption>Elenco ordini</caption>
                        <tr>
                            <th>ID</th>
                            <th>Data</th>
                            <th>Ispeziona</th>
                            <th>Elimina</th>
                        </tr>
                        <%
                            for (Ordine ordine: ordini) {
                        %>
                        <tr>
                            <td data-head="ID"> <%=ordine.getId()%> </td>
                            <td data-head="Data"> <%=ordine.getData().toString()%> </td>
                            <td data-head="Ispeziona">
                                <form action="${pageContext.request.contextPath}/manager/inspectOrdine"
                                      method="post">
                                    <input type="hidden" name="ordineid" value="<%=ordine.getId()%>">
                                    <input type="submit" value="Ispeziona">
                                </form>
                            </td>
                            <td data-head="Elimina">
                                <form action="${pageContext.request.contextPath}/manager/deleteOrdine" method="post">
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
                        <jsp:param name="resource" value="${pageContext.request.contextPath}/manager/showAllOrdine"/>
                    </jsp:include>
                </div>
            </section>
        </main>
    </body>
</html>
