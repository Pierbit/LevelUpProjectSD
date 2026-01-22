<%@ page import="model.categoria.Categoria" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" language="java" %>
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

            /* Next & previous buttons */
            .prev, .next {
                cursor: pointer;
                position: absolute;
                top: 50%;
                width: auto;
                padding: 16px;
                margin-top: -22px;
                color: white;
                font-weight: bold;
                font-size: 18px;
                transition: 0.6s ease;
                border-radius: 0 3px 3px 0;
                user-select: none;
            }

            /* Position the "next button" to the right */
            .next {
                right: 0;
                border-radius: 3px 0 0 3px;
            }

            /* On hover, add a black background color with a little bit see-through */
            .prev:hover, .next:hover {
                background-color: rgba(0,0,0,0.8);
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
                .prev, .next,.text {font-size: 11px}
            }

            .text > * {
                font-weight: bold;
                background-color: hsla(0,0%,0%,0.5);
                padding: .5rem;
                border-radius: 5px;
            }

            /* Card */
            .card {
                box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
                transition: 0.3s;
                width: 20rem;
                background-color: var(--shade3);
            }

            .card a {
                color: white;
                text-decoration: none;
            }

            .card:hover {
                box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2);
                cursor: pointer;
            }

            .container {
                padding: 2px 16px;
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

                    <!--
                    <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
                    <a class="next" onclick="plusSlides(1)">&#10095;</a>
                    -->
                </div>

                <script>
                    var slideIndex = 1;
                    showSlides(slideIndex);

                    /*
                    function plusSlides(n) {
                        showSlides(slideIndex += n);
                    }
                    */

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
                    <div class="card">
                        <a href="">
                            <img src="https://i.ytimg.com/vi/Teg70xwkX4I/hqdefault.jpg?sqp=-oaymwEcCOADEI4CSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLD5oWzX8GNYKE9SpbtwHgIE0godRQ" alt="card" style="width:100%">
                            <div class="container">
                                <h4><b>Come sviluppare una bestemmia creativa</b></h4>
                                <p>di germano_mosconi_666</p>
                            </div>
                        </a>
                    </div>
                </div>
                <%@include file="WEB-INF/views/partials/home/footer.jsp"%>
            </section>
        </main>
    </body>
</html>