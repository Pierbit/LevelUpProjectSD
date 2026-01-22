<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Crea una nuova categoria"/>
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
            <form action="${pageContext.request.contextPath}/manager/executeCreateCategoria"
                  method="post" class="app justify-center align-center">
                <fieldset class="grid-y cell w50 create">
                    <h2 id="login" style="text-align: center">Crea una nuova categoria</h2>

                    <label for="nome" class="field">Nome Categoria</label> <br>
                    <input type="text" id="nome" name="nome"> <br>
                    <p id="namerr" class="err">Il nome della categoria deve essere 3-30 caratteri e non può contenere
                        caratteri speciali o spazi.</p> <br>
                    <button type="submit" class="btn primary" onclick="return TagValidate()">Crea</button>
                </fieldset>
            </form>
        </div>
    </section>
</main>
</body>
</html>