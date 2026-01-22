<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Register"/>
        <jsp:param name="scripts" value="validators.js"/>
    </jsp:include>

    <script>
        function validate() {
            let pattern = /^[\w.!-]{5,20}$/gm;
            let teststring = document.getElementById("username").value;
            let pattern1 = /^(?=.*[A-Z])[\w.!-]{8,}$/;
            let teststring1 = document.getElementById("password").value;
            let valid = pattern.test(teststring);
            let valid1 = pattern1.test(teststring1);
            if (valid && valid1) {
                document.getElementById("namerr").style.display = "none";
                document.getElementById("passerr").style.display = "none";
                return true;
            } else if(!valid && valid1) {
                document.getElementById("namerr").style.display = "block";
                return false;
            } else if(valid && !valid1) {
                document.getElementById("passerr").style.display = "block";
                return false;
            } else {
                document.getElementById("namerr").style.display = "block";
                document.getElementById("passerr").style.display = "block";
                return false;
            }
        }
    </script>

    <style>
        p.err {
            color: red;
            display: none;
            font-size: 12px;
            font-family: "Source Sans Pro";
        }

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

<body class="bgr">

<c:if test="${not empty alert}">
    <%@include file="/WEB-INF/views/partials/alert.jsp" %>
</c:if>

<form action="${pageContext.request.contextPath}/accounts/register" method="post"
      class="app grid-x justify-center align-center">
    <fieldset class="grid-y cell w40 logging">
        <h2 style="text-align: center">Registrati a levelUp!</h2>

        <label for="username" class="field">Nome utente</label> <br>
        <input type="text" name="username" id="username"> <br>
        <p id="namerr" class="err">L'username deve essere lungo 5-20 caratteri e non può
            contenere caratteri speciali oltre (./_/!)</p> <br>
        <label for="email" class="field">Email</label> <br>
        <input type="email" name="email" id="email" required> <br>
        <label for="password" class="field">Password</label> <br>
        <input type="password" name="password" id="password"> <br>
        <p id="passerr" class="err">La password deve essere lunga almeno otto caratteri e
            contenere almeno una lettera maiuscola.</p> <br>
        <button type="submit" class="btn primary" onclick="return RegisterValidate()"> Registrati</button>

        <div>
            <p>Hai già un account?
                <a href="${pageContext.request.contextPath}/accounts/login">Accedi</a></p>
            <a href="${pageContext.request.contextPath}">Torna alla home</a>
        </div>
    </fieldset>
</form>
</body>
</html>
