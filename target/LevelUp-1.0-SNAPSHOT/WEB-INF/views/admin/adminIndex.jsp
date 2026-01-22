<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!doctype html>

<html lang="it">
    <head>
        <jsp:include page="../partials/head.jsp">
            <jsp:param name="title" value="Pagina amministrazione"/>
            <jsp:param name="styles" value="admin.css"/>
            <jsp:param name="scripts" value="admin.js"/>
        </jsp:include>

        <style>
            .body > div {
                background-color: white;
                margin: 0.5rem;
                font-weight: bold;
                text-align: center;
                border-radius: 5px;
            }

            .body > div > h4 {
                margin: 0;
                padding: 1rem;
                color: white;
                background-color: var(--shade3);
                border-radius: 5px 5px 0 0;
            }

            .body > div > h2 {
                justify-self: center;
                font-size: 3rem;
            }
        </style>
    </head>

    <body>
        <main class="app">
            <section class="content grid-y">
                <%@include file="../partials/admin/header.jsp"%>

                <%@include file="../partials/admin/navbar.jsp"%>

                <div class="body grid-x justify-center">
                </div>

                <%@include file="../partials/admin/footer.jsp"%>
            </section>
        </main>
    </body>
</html>
