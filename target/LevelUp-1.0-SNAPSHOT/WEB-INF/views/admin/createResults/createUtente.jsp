<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <jsp:include page="../../partials/head.jsp">
            <jsp:param name="title" value="Crea un nuovo utente"/>
            <jsp:param name="styles" value="admin.css,showAll.css"/>
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

                <div class="body grid-x justify-center">
                    <form action="${pageContext.request.contextPath}/manager/createUtente"
                          method="post" class="app justify-center align-center" enctype="multipart/form-data">
                        <fieldset class="grid-y cell w50 create">
                            <h2 id="login" style="text-align: center">Crea un nuovo utente</h2>

                            <label for="username" class="field">Nickname dell'utente</label> <br>
                                <input type="text" minlength="5" maxlength="50" id="username" name="username"> <br>
                            <p id="namerr" class="err">L'username deve essere lungo 5-20 caratteri e non può
                                contenere caratteri speciali oltre (./_/!)</p> <br>
                            <label for="email" class="field">Indirizzo email</label> <br>
                                <input type="email" id="email" name="email" required> <br>
                            <label for="password" class="field">Password</label> <br>
                                <input type="password" id="password" name="password"> <br>
                            <p id="passerr" class="err">La password deve essere lunga almeno otto caratteri e
                                contenere almeno una lettera maiuscola.</p> <br>
                            <p>Amministratore</p> <br>
                                <input type="radio" id="true" name="admin" value="true">
                                <label for="true">Sì</label> <br>
                                <input type="radio" id="false" name="admin" value="false">
                                <label for="true">No</label> <br> <br>
                            <label for="cover" class="field">Foto profilo</label> <br>
                            <input type="file"
                                   accept=".apng, .avif, .gif, .jpg, .jpeg, .jfif, .pjpeg, .pjp, .png, .svg, .webp"
                                   id="cover" name="cover"> <br> <br>
                            <button type="submit" class="btn primary" onclick="return UtenteValidate()">Crea</button>
                        </fieldset>
                    </form>
                </div>
            </section>
        </main>
    </body>
</html>
