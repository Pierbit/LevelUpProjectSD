<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="WEB-INF/views/partials/head.jsp">
        <jsp:param name="title" value="Home"/>
    </jsp:include>

    <script>
        function userLogged() {
            const nick = document.getElementById("userNick").getAttribute("value");
            if (nick === "") {
                document.getElementById("login").style.display = "block";
                document.getElementById("loggato").style.display = "none";
                document.getElementById("adminlogged").style.display = "none";
            } else {
                if (document.getElementById("userAdmin").getAttribute("value") === "true") {
                    document.getElementById("login").style.display = "none";
                    document.getElementById("loggato").style.display = "none";
                    document.getElementById("adminlogged").style.display = "block";
                } else {
                    document.getElementById("login").style.display = "none";
                    document.getElementById("loggato").style.display = "block";
                    document.getElementById("adminlogged").style.display = "none";
                }
            }
        }
    </script>

    <style>
        .bgr {
            background: linear-gradient(var(--shade5), var(--shade1));
        }

        .mainpage {
            padding: 1rem;
            background-color: white;
            border-radius: 5px;
        }

        .mainpage > * {
            margin: 3px;
        }
    </style>
</head>

<body onload="userLogged()" class="bgr">
<div class="app grid-x justify-center align-center">
    <fieldset class="grid-y cell w40 mainpage">
        <h1 style="text-align: center"> Benvenuto su levelUp! </h1>

        <div id="login">
            <a href="${pageContext.request.contextPath}/accounts/login">Login</a><br>
            <a href="${pageContext.request.contextPath}/accounts/register">Registrati</a>
        </div>

        <div id="loggato">
            <p>Ciao, ${utente.nickname}!</p>
            <a href="${pageContext.request.contextPath}/user/profile">Vai al profilo</a>
            <a href="${pageContext.request.contextPath}/accounts/logout">Logout</a>
        </div>

        <div id="adminlogged">
            <a href="${pageContext.request.contextPath}/manager/goindex">
                Pagina di amministrazione</a><br>
            <a href="${pageContext.request.contextPath}/accounts/logout">Logout</a>
        </div>
    </fieldset>
</div>

<input type="hidden" id="userNick" name="userNick" value="${utente.nickname}">
<input type="hidden" id="userAdmin" name="userAdmin" value="${utente.manager}">
</body>
</html>