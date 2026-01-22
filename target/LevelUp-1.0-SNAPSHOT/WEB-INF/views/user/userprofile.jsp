<%@ page import="model.utente.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../partials/head.jsp">
        <jsp:param name="title" value="Profilo utente"/>
        <jsp:param name="styles" value="user.css"/>
    </jsp:include>

    <script>
        function isPublisher() {
            var publisher = document.getElementById("publisher").getAttribute("value");
            //var justunsub = document.getElementById("unsubbed").getAttribute("value");
            if (publisher == "false") {
                document.getElementById("nocontentcreator").style.display = "block";
            } else {
                document.getElementById("nocontentcreator").style.display = "none";
            }
            /*if (justunsub == "true") {
                document.getElementById("justunsub").style.display = "block";
            } else {
                document.getElementById("justunsub").style.display = "none";
            }*/

        }
    </script>

    <style>
        /*
        body {
            background: linear-gradient(var(--shade5), var(--shade1));
        }*/

        .par {
            background: white;
            padding: 1rem;
            margin: .5rem;
            border-radius: 3px;
        }

        .head {
            justify-content: center;
        }

        .head * {
            margin: .5rem;
        }

        .head img {
            max-width: 300px;
            max-height: 300px;
            width: 15rem;
            max-height: 300px;
            height: auto;
        }

        .head h1 {
            border-bottom: 4px var(--shade3) solid;
        }

        /*
        @media only screen and (min-width: 769px) {
            .head {
                flex-wrap: nowrap;
                justify-content: left;
            }
        }*/
    </style>

</head>
<body onload="isPublisher()">
<main class="app">
    <%
        Utente utente = (Utente) session.getAttribute("utente");
    %>

    <section class="content grid-y">
        <%@include file="../partials/user/header.jsp"%>
        <div class="body grid-y justify-center">
            <div class="par head grid-y justify-center align-center">
                <%
                    if ((utente.getFotoProfilo()) == null) {
                %>
                <img alt="profilepicture" src="${pageContext.request.contextPath}/images/no_avatar.jpg">
                <%
                } else {
                %>
                <img alt="profilepicture" src="${pageContext.request.contextPath}/covers/<%=utente.getFotoProfilo()%>">
                <%
                    }
                %>

                <div>
                    <h1><%=utente.getNickname()%></h1>
                    <%
                        if (utente.getBiografia() != null) {
                    %>
                    <p><%=utente.getBiografia()%></p>
                    <%
                        }
                    %>
                </div>
            </div>

            <div class="par grid-x align-center">
                <p id="nocontentcreator" style="font-style: italic">
                    Non hai ancora pubblicato alcun corso...
                    <a href="${pageContext.request.contextPath}/user/createCorso">
                        diventa un content creator su levelUp!
                    </a>
                </p>
            </div>
        </div>

        <!--<div id="justunsub">
            <p style="font-style: italic">Ti sei disiscritto con successo!</p>
        </div>-->
        <%@include file="../partials/user/footer.jsp"%>
    </section>

    <input type="hidden" id="publisher" name="userPublisher" value="${publisher}">
    <!--<input type="hidden" id="unsubbed" name="unsubbed" value="${justunsub}">-->
</main>
</body>
</html>
