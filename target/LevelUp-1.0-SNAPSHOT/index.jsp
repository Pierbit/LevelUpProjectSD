<%@ page import="model.categoria.Categoria" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.corso.Corso" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="WEB-INF/views/partials/head.jsp">
            <jsp:param name="title" value="Home"/>
            <jsp:param name="styles" value="user.css"/>
            <jsp:param name="scripts" value="home.js"/>
        </jsp:include>

        <style>
            /* Slideshow container */
            .slideshow-container {
                width: 100%;
                position: relative;
                margin: auto;
            }

            /* Caption text */
            .text {
                color: #f2f2f2;
                font-size: 15px;
                padding: 8px 12px;
                position: absolute;
                bottom: 8px;
                width: 100%;
                text-align: center;
            }

            /* Fading animation */
            .fade {
                -webkit-animation-name: fade;
                -webkit-animation-duration: 1.5s;
                animation-name: fade;
                animation-duration: 1.5s;
            }

            @-webkit-keyframes fade {
                from {opacity: .4}
                to {opacity: 1}
            }

            @keyframes fade {
                from {opacity: .4}
                to {opacity: 1}
            }

            /* On smaller screens, decrease text size */
            @media only screen and (max-width: 300px) {
                .text {font-size: 11px}
            }

            .text > * {
                font-weight: bold;
                background-color: hsla(0,0%,0%,0.5);
                padding: .5rem;
                border-radius: 5px;
            }

            .body {
                flex-wrap: wrap;
            }
        </style>
    </head>
    <body onload="loadCategorie(); setInterval(plusSlides, 7000);">
        <main class="app">
            <section class="content grid-y">
                <%@include file="WEB-INF/views/partials/home/header.jsp"%>
                <%@include file="WEB-INF/views/partials/home/categoriebar.jsp"%>

                <div class="slideshow-container">
                    <div class="mySlides fade">
                        <img src="${pageContext.request.contextPath}/images/index_slideshow/slide1.png" style="width:100%">
                        <div class="text">
                            <h1>Benvenuto su levelUp!</h1>
                        </div>
                    </div>

                    <div class="mySlides fade">
                        <img src="${pageContext.request.contextPath}/images/index_slideshow/slide2.png" style="width:100%">
                        <div class="text">
                            <h1>Scopri nuove possibilità!</h1>
                        </div>
                    </div>

                    <div class="mySlides fade">
                        <img src="${pageContext.request.contextPath}/images/index_slideshow/slide3.png" style="width:100%">
                        <div class="text">
                            <h1>Supera i tuoi limiti!</h1>
                        </div>
                    </div>
                </div>

                <script>
                    var slideIndex = 1;
                    showSlides(slideIndex);

                    function plusSlides() {
                        showSlides(slideIndex += 1);
                    }

                    function showSlides(n) {
                        var i;
                        var slides = document.getElementsByClassName("mySlides");
                        if (n > slides.length) {slideIndex = 1}
                        if (n < 1) {slideIndex = slides.length}
                        for (i = 0; i < slides.length; i++) {
                            slides[i].style.display = "none";
                        }
                        slides[slideIndex-1].style.display = "block";
                    }
                </script>

                <div class="body grid-x justify-center align-center">

                    <jsp:include page="/WEB-INF/views/partials/cards/corso.jsp">
                        <jsp:param name="id" value="3"/>
                        <jsp:param name="cover" value="2021-07-26 22.10.16.314 hacking.jpg"/>
                        <jsp:param name="title" value="How to become an hacker"/>
                        <jsp:param name="user" value="hackerino33"/>
                        <jsp:param name="price" value="50"/>
                    </jsp:include>

                    <jsp:include page="/WEB-INF/views/partials/cards/corso.jsp">
                        <jsp:param name="id" value="4"/>
                        <jsp:param name="cover" value="2021-07-26 22.15.36.867 cool-guy-wallpaper-1280x720_45.jpg"/>
                        <jsp:param name="title" value="How to be cool"/>
                        <jsp:param name="user" value="bigchad1"/>
                        <jsp:param name="price" value="75"/>
                    </jsp:include>

                    <jsp:include page="/WEB-INF/views/partials/cards/corso.jsp">
                        <jsp:param name="id" value="14"/>
                        <jsp:param name="cover" value="2021-07-26 22.39.27.671 chess.jpg"/>
                        <jsp:param name="title" value="How to improve at chess"/>
                        <jsp:param name="user" value="therealL"/>
                        <jsp:param name="price" value="12.25"/>
                    </jsp:include>

                    <jsp:include page="/WEB-INF/views/partials/cards/corso.jsp">
                        <jsp:param name="id" value="10"/>
                        <jsp:param name="cover" value="2021-07-26 22.29.10.022 gaming.jpg"/>
                        <jsp:param name="title" value="How to build a gaming PC"/>
                        <jsp:param name="user" value="hackerino33"/>
                        <jsp:param name="price" value="70.5"/>
                    </jsp:include>

                    <jsp:include page="/WEB-INF/views/partials/cards/corso.jsp">
                        <jsp:param name="id" value="7"/>
                        <jsp:param name="cover" value="2021-07-26 22.20.57.884 football.jpg"/>
                        <jsp:param name="title" value="How to play football"/>
                        <jsp:param name="user" value="bigchad1"/>
                        <jsp:param name="price" value="26.25"/>
                    </jsp:include>

                    <jsp:include page="/WEB-INF/views/partials/cards/corso.jsp">
                        <jsp:param name="id" value="6"/>
                        <jsp:param name="cover" value="2021-07-26 22.19.11.447 basket.jpg"/>
                        <jsp:param name="title" value="How to play BasketBall"/>
                        <jsp:param name="user" value="bigchad1"/>
                        <jsp:param name="price" value="60.5"/>
                    </jsp:include>

                </div>

                <%@include file="WEB-INF/views/partials/home/footer.jsp"%>
            </section>
        </main>
    </body>
</html>