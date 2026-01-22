<%@ page import="model.corso.Corso" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Modifica corso"/>
        <jsp:param name="styles" value="admin.css,showAll.css"/>
        <jsp:param name="scripts" value="admin.js,validators.js"/>
    </jsp:include>
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
                Corso corso = (Corso) request.getAttribute("corso");
            %>
            <form action="${pageContext.request.contextPath}/user/executeUpdateCorso"
                  method="post" class="app justify-center align-center" enctype="multipart/form-data">
                <fieldset class="grid-y cell w50 create">
                    <h2 id="login" style="text-align: center">Aggiorna il corso selezionato</h2>

                    <label for="nome" class="field">Nome del corso</label> <br>
                    <input type="text" minlength="5" maxlength="50" id="nome" name="nome" value="${corso.nome}"> <br> <br>
                    <label for="prezzo" class="field">Prezzo di base</label> <br>
                    <input type="number" step=".25" id="prezzo" name="prezzo" value="${corso.prezzoBase}"> <br> <br>
                    <label for="content" class="field">Contenuto</label> <br>
                    <textarea id="content" rows="10" maxlength="10000" name="content">${corso.testo}</textarea> <br> <br>
                    <label for="cover" class="field">Immagine di copertina</label> <br>
                    <input type="file"
                           accept=".apng, .avif, .gif, .jpg, .jpeg, .jfif, .pjpeg, .pjp, .png, .svg, .webp"
                           id="cover" name="cover"> <br> <br>
                    <input type="hidden" name="corsoid" value="${corso.id}">
                    <button type="submit" class="btn primary" onclick="return CorsoValidate">Aggiorna</button>
                </fieldset>
            </form>
        </div>
    </section>
</main>
</body>
</html>
