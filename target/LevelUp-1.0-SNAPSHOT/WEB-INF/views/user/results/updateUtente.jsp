<%@ page import="model.categoria.Categoria" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.categoria.CategoriaManager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Crea un corso"/>
        <jsp:param name="styles" value="user.css"/>
        <jsp:param name="scripts" value="home.js,admin.js,validators.js"/>
    </jsp:include>

    <style>
        .logging {
            margin-bottom: 1rem;
        }

        .logging > * {
            margin: .5rem;
        }

        .box {
            box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
            width: 90%;
            background-color: white;
        }

        .box .container {
            padding: 2px 16px;
            color: black;
        }

        @media only screen and (min-width: 769px) {
            .box {
                width: 70%;
            }
        }
    </style>
</head>

<body onload="loadCategorie()">
<%
    Utente utente = (Utente) session.getAttribute("utente");
%>
<main class="app">
    <section class="content grid-y">
        <%@include file="/WEB-INF/views/partials/home/header.jsp"%>
        <%@include file="/WEB-INF/views/partials/home/categoriebar.jsp"%>

        <c:if test="${not empty alert}">
            <%@include file="/WEB-INF/views/partials/alert.jsp" %>
        </c:if>

        <div class="body grid-y align-center">
            <div class="box">
                <div class="container">
                    <h2 style="text-align: center">Aggiorna i tuoi dati personali</h2>

                    <form action="${pageContext.request.contextPath}/user/executeUpdateUtente" method="post"
                          class="grid-x justify-center" enctype="multipart/form-data">
                        <div class="grid-y cell w80 logging">
                            <label for="username" class="field">Nickname</label> <br>
                            <input type="text" id="username" name="username" value="<%=utente.getNickname()%>"
                                   readonly>
                            <p id="namerr" class="err">L'username deve essere lungo 5-20 caratteri e non può
                                contenere caratteri speciali oltre (./_/!)</p> <br>
                            <label for="email" class="field">Email</label> <br>
                            <input type="email" id="email" name="email" placeholder="Nuova Email" required> <br>
                            <label for="password" class="field">Password</label> <br>
                            <input type="password" id="password" name="password" placeholder="Nuova password">
                            <br>
                            <p id="passerr" class="err">La password deve essere lunga almeno otto caratteri e
                                contenere almeno una lettera maiuscola.</p> <br>
                            <label for="bio" class="field">Biografia</label> <br>
                            <textarea id="bio" rows="10" maxlength="10000"
                                      name="bio"><%=utente.getBiografia()%></textarea> <br>
                            <label for="cover" class="field">Immagine di profilo</label> <br>
                            <input type="file"
                                   accept=".apng, .avif, .gif, .jpg, .jpeg, .jfif, .pjpeg, .pjp, .png, .svg, .webp"
                                   id="cover" name="cover"> <br> <br>
                            <button type="submit" class="btn primary" onclick="return UtenteValidate()">
                                Aggiorna
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <%@include file="/WEB-INF/views/partials/home/footer.jsp"%>
    </section>
</main>
</body>
</html>
