<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../../partials/head.jsp">
        <jsp:param name="title" value="Crea un nuovo tag"/>
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

        <div class="body grid-y align-center">
            <div class="box">
                <div class="container">
                    <h2 style="text-align: center">Crea un nuovo tag</h2>
                    <form action="${pageContext.request.contextPath}/manager/executeCreateTag"
                          method="post" class="grid-x justify-center">
                        <div class="grid-y cell w80 logging">
                            <label for="nome" class="field">Nome Tag</label> <br>
                            <input type="text" id="nome" name="nome"> <br>
                            <p id="namerr" class="err">Il nome del tag deve essere 3-30 caratteri e non può contenere
                                caratteri speciali o spazi.</p> <br>
                            <button type="submit" class="btn primary" onclick="return TagValidate()">Crea</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>
</body>
</html>