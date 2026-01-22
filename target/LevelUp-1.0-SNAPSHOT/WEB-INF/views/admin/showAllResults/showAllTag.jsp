<%@ page import="java.util.List" %>
<%@ page import="model.tag.Tag" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="../../partials/head.jsp">
            <jsp:param name="title" value="Elenco dei tag"/>
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
                        List<Tag> tags = (List<Tag>) request.getAttribute("elencoTags");
                    %>

                    <table class="table">
                        <caption>Elenco tag</caption>
                        <tr>
                            <th>Nome</th>
                            <th>Ispeziona</th>
                            <th>Elimina</th>
                        </tr>
                        <%
                            for (Tag tag: tags) {
                        %>
                        <tr>
                            <td data-head="Nome"> <%=tag.getNome()%> </td>
                            <td data-head="Ispeziona">
                                <form action="${pageContext.request.contextPath}/manager/inspectTag"
                                      method="post">
                                    <input type="hidden" name="nome" value="<%=tag.getNome()%>">
                                    <input type="submit" value="Ispeziona">
                                </form>
                            </td>
                            <td data-head="Elimina">
                                <form action="${pageContext.request.contextPath}/manager/deleteTag" method="post">
                                    <input type="hidden" name="nome" value="<%=tag.getNome()%>">
                                    <input type="submit" value="Elimina">
                                </form>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>

                    <a href="${pageContext.request.contextPath}/manager/createTag">
                        Inserisci un nuovo tag.
                    </a>

                    <jsp:include page="/WEB-INF/views/partials/paginator.jsp">
                        <jsp:param name="resource" value="${pageContext.request.contextPath}/manager/showAllTag"/>
                    </jsp:include>
                </div>
            </section>
        </main>
    </body>
</html>
