<%@ page import="model.utente.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Modifica dati personali"/>
        <jsp:param name="styles" value="admin.css,showAll.css"/>
        <jsp:param name="scripts" value="admin.js,validators.js"/>
    </jsp:include>

    <style>
        p.err {
            color:red;
            display: none;
            font-size: 12px;
            font-family: "Source Sans Pro";
        }

        .logging > * {
            margin: 3px;
        }
    </style>
</head>

<body>
<main class="app">
    <section class="content grid-y">
        <%@include file="../../partials/user/header.jsp"%>

        <c:if test="${not empty alert}">
            <%@include file="/WEB-INF/views/partials/alert.jsp" %>
        </c:if>

        <div class="body grid-x justify-center">
            <%
                Utente utente = (Utente) session.getAttribute("utente");
            %>
            <form action="${pageContext.request.contextPath}/user/executeUpdateUtente"
                  method="post" class="app justify-center align-center" enctype="multipart/form-data">
                <fieldset class="grid-y cell w50 create">
                    <h2 id="login" style="text-align: center">Aggiorna i tuoi dati personali</h2>

                    <label for="username" class="field">Nickname</label> <br>
                    <input type="text" id="username" name="username" value="<%=utente.getNickname()%>" readonly>
                    <p id="namerr" class="err">L'username deve essere lungo 5-20 caratteri e non può
                        contenere caratteri speciali oltre (./_/!)</p> <br>
                    <label for="email" class="field">Email</label> <br>
                    <input type="email" id="email" name="email" placeholder="Nuova Email" required> <br>
                    <label for="password" class="field">Password</label> <br>
                    <input type="password" id="password" name="password" placeholder="Nuova password"> <br>
                    <p id="passerr" class="err">La password deve essere lunga almeno otto caratteri e
                        contenere almeno una lettera maiuscola.</p> <br>
                    <label for="bio" class="field">Biografia</label> <br>
                    <textarea id="bio" rows="10" maxlength="10000" name="bio"><%=utente.getBiografia()%></textarea> <br>
                    <label for="cover" class="field">Immagine di profilo</label> <br>
                    <input type="file"
                           accept=".apng, .avif, .gif, .jpg, .jpeg, .jfif, .pjpeg, .pjp, .png, .svg, .webp"
                           id="cover" name="cover"> <br> <br>
                    <button type="submit" class="btn primary" onclick="return UtenteValidate()">Aggiorna</button>
                </fieldset>
            </form>
        </div>
    </section>
</main>
</body>
</html>
