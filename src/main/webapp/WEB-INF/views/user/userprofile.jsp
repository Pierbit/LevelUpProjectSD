<%@ page import="model.categoria.Categoria" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.corso.Corso" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="../partials/head.jsp">
            <jsp:param name="title" value="Profilo"/>
            <jsp:param name="styles" value="user.css"/>
            <jsp:param name="scripts" value="home.js"/>
        </jsp:include>

        <style>
            .identity {
                box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
                background-color: var(--shade3);
                width: 25rem;
            }

            .identity > .name-bio {
                padding: 2px 16px;
                color: white;
            }

            .name-bio > h4 {
                text-align: center;
            }

            .courses {
                box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
                background-color: white;
                width: 90%;
                padding: .5rem 1rem 2rem;
                color: black;
            }

            .courses > h2 {
                text-align: center;
            }

            .courses > div > * {
                margin: .5rem;
            }
        </style>
    </head>

    <body onload="loadCategorie()">
    <%
        Utente utente = (Utente) request.getAttribute("utente");
    %>
        <main class="app">
            <section class="content grid-y">
                <%@include file="../partials/home/header.jsp"%>
                <%@include file="../partials/home/categoriebar.jsp"%>

                <div class="body grid-y justify-center align-center">
                    <div class="identity">
                        <% if (utente.getFotoProfilo() == null) { %>
                            <img src="${pageContext.request.contextPath}/images/no_avatar.jpg" alt="avatar" style="width:100%">
                        <% } else { %>
                            <img src="${pageContext.request.contextPath}/covers/<%=utente.getFotoProfilo()%>" alt="avatar" style="width:100%">
                        <% } %>
                        <div class="name-bio">
                            <h4><b><%=utente.getNickname()%></b></h4>
                            <% if (utente.getBiografia() != null) { %>
                                <p><%=utente.getBiografia()%></p>
                            <% } %>
                        </div>
                    </div>

                    <% if (!utente.getCorsiCreati().isEmpty()) { %>
                        <div class="courses">
                            <h2>Corsi creati</h2>

                            <div class="grid-x align-center justify-center">
                                <% for (Corso corso: utente.getCorsiCreati()) { %>
                                    <jsp:include page="../partials/cards/corso.jsp">
                                        <jsp:param name="id" value="<%=corso.getId()%>"/>
                                        <jsp:param name="cover" value="<%=corso.getCopertina()%>"/>
                                        <jsp:param name="title" value="<%=corso.getNome()%>"/>
                                        <jsp:param name="user" value="<%=corso.getUtenteCreatore().getNickname()%>"/>
                                        <jsp:param name="price" value="<%=corso.getPrezzoBase()%>"/>
                                    </jsp:include>
                                <% } %>
                            </div>
                        </div>
                    <% } %>

                    <% if (!utente.getCorsiPartecipati().isEmpty()) { %>
                        <div class="courses">
                            <h2>Partecipazioni</h2>

                            <div class="grid-x align-center justify-center">
                                <% for (Corso corso: utente.getCorsiPartecipati()) { %>
                                <jsp:include page="../partials/cards/corso.jsp">
                                    <jsp:param name="id" value="<%=corso.getId()%>"/>
                                    <jsp:param name="cover" value="<%=corso.getCopertina()%>"/>
                                    <jsp:param name="title" value="<%=corso.getNome()%>"/>
                                    <jsp:param name="user" value="<%=corso.getUtenteCreatore().getNickname()%>"/>
                                    <jsp:param name="price" value="<%=corso.getPrezzoBase()%>"/>
                                </jsp:include>
                                <% } %>
                            </div>
                        </div>
                    <% } %>
                </div>

                <%@include file="../partials/home/footer.jsp"%>
            </section>
        </main>
    </body>
</html>