<%@ page import="model.utente.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <jsp:include page="../../partials/head.jsp">
            <jsp:param name="title" value="Aggiorna i dati dell'utente"/>
            <jsp:param name="styles" value="user.css,admin.css,showAll.css"/>
            <jsp:param name="scripts" value="admin.js,validators.js"/>
        </jsp:include>
    </head>

    <body>
        <main class="app">
            <section class="content grid-y">
                <%@include file="../../partials/admin/header.jsp"%>

                <%@include file="../../partials/admin/navbar.jsp"%>

                <c:if test="${not empty alert}">
                    <%@include file="/WEB-INF/views/partials/alert.jsp" %>
                </c:if>

                <%
                    Utente user = (Utente) request.getAttribute("user");
                %>

                <div class="body grid-y align-center">
                    <div class="box">
                        <div class="container">
                            <h2 style="text-align: center">Aggiorna i dati dell'utente</h2>

                            <form action="${pageContext.request.contextPath}/manager/executeUpdateUtente" method="post"
                                  class="grid-x justify-center" enctype="multipart/form-data">
                                <div class="grid-y cell w80 logging">
                                    <label for="username" class="field">Nickname dell'utente</label>
                                    <input type="text" minlength="5" maxlength="50" id="username" name="username" value="<%=user.getNickname()%>" readonly>
                                    <p id="namerr" class="err">L'username deve essere lungo 5-20 caratteri e non può
                                        contenere caratteri speciali oltre (./_/!)</p>
                                    <label for="email" class="field">Indirizzo email</label>
                                    <input type="email" id="email" name="email" value="<%=user.getEmail()%>" required>
                                    <label for="password" class="field">Password</label>
                                    <input type="password" id="password" name="password">
                                    <p id="passerr" class="err">La password deve essere lunga almeno otto caratteri e
                                        contenere almeno una lettera maiuscola.</p>
                                    <p>Amministratore</p>
                                    <div class="grid-x">
                                        <input type="radio" id="true" name="admin" value="true">
                                        <label for="true"> Sì</label>
                                    </div>
                                    <div class="grid-x">
                                        <input type="radio" id="false" name="admin" value="false" checked>
                                        <label for="false"> No</label>
                                    </div>
                                    <label for="cover" class="field">Foto profilo</label>
                                    <input type="file"
                                           accept=".apng, .avif, .gif, .jpg, .jpeg, .jfif, .pjpeg, .pjp, .png, .svg, .webp"
                                           id="cover" name="cover">
                                    <input type="hidden" id="nickname" name="nickname" value="<%=user.getNickname()%>">
                                    <button type="submit" class="btn primary" onclick="return UtenteValidate()">Aggiorna</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    </body>
</html>
