<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <jsp:include page="../partials/head.jsp">
            <jsp:param name="title" value="Login"/>
            <jsp:param name="scripts" value="validators.js"/>
        </jsp:include>

        <script>
            function justRegistered() {
                var x = document.getElementById("justreg").getAttribute("value");
                if(x != "") {
                    document.getElementById("login").innerHTML =
                        "Ti sei registrato con successo! Effettua ora il login.";
                }
            }
        </script>

        <style>
            .bgr {
                background: linear-gradient(var(--shade5), var(--shade1));
            }

            .logging {
                padding: 1rem;
                background-color: white;
                border-radius: 5px;
                border: none;
            }

            .logging > * {
                margin: 3px;
            }
        </style>
    </head>

    <body onload="justRegistered()" class="bgr">

    <c:if test="${not empty alert}">
        <%@include file="/WEB-INF/views/partials/alert.jsp" %>
    </c:if>

        <form action="${pageContext.request.contextPath}/accounts/login" method="post"
              class="app grid-x justify-center align-center">
            <fieldset class="grid-y cell w40 logging">
                <h2 id="login" style="text-align: center">Login</h2>

                <label for="username" class="field"> Nome utente </label> <br>
                    <input type="text" id="username" name="username"> <br>
                <label for="password" class="field"> Password </label> <br>
                    <input type="password" id="password" name="password"> <br>
                <p class="err" id="errore">Riempi tutti i campi</p>
                <button type="submit" class="btn primary" onclick="return LoginValidate()"> Accedi </button>

                <div>
                    <p>Non sei ancora un membro?
                        <a href="${pageContext.request.contextPath}/accounts/register">Iscriviti subito</a></p>
                    <a href="${pageContext.request.contextPath}/">Torna alla home</a>
                </div>
            </fieldset>
        </form>

        <input type="hidden" id="justreg" name="justreg" value="${justreg}">
    </body>
</html>
