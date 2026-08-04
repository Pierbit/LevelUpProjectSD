<%@ page import="java.util.List" %>
<%@ page import="model.corso.Corso" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.tag.Tag" %>
<%@ page import="model.categoria.Categoria" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/WEB-INF/views/partials/head.jsp">
            <jsp:param name="title" value="Elenco dei corsi"/>
            <jsp:param name="styles" value="user.css"/>
            <jsp:param name="scripts" value="home.js"/>
        </jsp:include>

        <style>
            .logging {
                margin-bottom: .5rem;
            }

            .logging > * {
                margin: .5rem;
            }

            .card {
                margin: .5rem;
            }

            .box {
                box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
                width: 95%;
                background-color: white;
                padding-bottom: .5rem;
            }

            .box .container {
                padding: .5rem;
                color: white;
            }

            @media only screen and (min-width: 769px) {
                .box {
                    width: 80%;
                }
            }
        </style>

    </head>
    <body class="home grid-y" onload="loadCategorie()">
        <%
            ArrayList<Tag> tags = (ArrayList<Tag>) request.getAttribute("tags");
            String categoriacercata = (String) request.getAttribute("categoriacercata");
            ArrayList<Corso> corsicercati = (ArrayList<Corso>) request.getAttribute("corsicercati");
        %>
        <main class="app">
            <section class="content grid-y">
                <%@include file="/WEB-INF/views/partials/home/header.jsp" %>
                <%@include file="/WEB-INF/views/partials/home/categoriebar.jsp" %>

                <div class="body grid-y align-center justify-center">
                    <div class="box">
                        <div class="container" style="color: black">
                            <form action="${pageContext.request.contextPath}/home/browseCorsi" class="grid-x justify-center">
                                <div class="grid-y cell w80 logging">
                                    <label for="creatorName">Nome content creator</label>
                                    <input type="text" id="creatorName" name="creatorName" placeholder="Content creator">
                                    <label for="corsoName">Nome del corso</label>
                                    <input type="text" id="corsoName" name="corsoName" placeholder="Nome corso">
                                    <label for="minPrice">Prezzo</label>
                                    <input type="number" id="minPrice" name="minPrice" placeholder="min" min="0">
                                    <input type="number" id="maxPrice" name="maxPrice" placeholder="max" min="0">
                                    <input type="hidden" id="categoriaName" name="categoriaName" value="<%=categoriacercata%>">
                                    <label for="tagNames">Tags</label>
                                    <select name="tagNames" id="tagNames" multiple>
                                        <%
                                            for (Tag tag : tags) {
                                        %>
                                        <option value="<%=tag.getNome()%>"><%=tag.getNome()%>
                                        </option>
                                        <%
                                            }
                                        %>
                                    </select>
                                    <button type="submit" class="btn primary">Cerca</button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="box" style="flex: 1">
                        <h2 style="text-align: center"><%=categoriacercata%></h2>

                        <div class="container">
                            <div class="grid-x align-center justify-center">
                                <% for (Corso corso: corsicercati) { %>
                                <jsp:include page="/WEB-INF/views/partials/cards/corso.jsp">
                                    <jsp:param name="id" value="<%=corso.getId()%>"/>
                                    <jsp:param name="cover" value="<%=corso.getCopertina()%>"/>
                                    <jsp:param name="title" value="<%=corso.getNome()%>"/>
                                    <jsp:param name="user" value="<%=corso.getUtenteCreatore().getNickname()%>"/>
                                    <jsp:param name="price" value="<%=corso.getPrezzoBase()%>"/>
                                </jsp:include>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>

                <%@include file="/WEB-INF/views/partials/home/footer.jsp" %>
            </section>
        </main>
    </body>
</html>
