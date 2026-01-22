<%@ page import="model.categoria.Categoria" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="model.categoria.CategoriaManager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
  <head>
    <jsp:include page="../partials/head.jsp">
      <jsp:param name="title" value="Crea un corso"/>
      <jsp:param name="styles" value="user.css"/>
      <jsp:param name="scripts" value="home.js,admin.js,validators.js"/>
    </jsp:include>

    <style>
      .logging {
        margin-bottom: 1rem;
      }

      .logging > * {
        margin: .5rem;
      }

      .box {
        box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
        width: 90%;
        background-color: white;
      }

      .box .container {
        padding: 2px 16px;
        color: black;
      }

      @media only screen and (min-width: 769px) {
        .box {
          width: 70%;
        }
      }
    </style>
  </head>

  <body onload="loadCategorie()">

    <main class="app">
      <section class="content grid-y">
        <%@include file="../partials/home/header.jsp"%>
        <%@include file="../partials/home/categoriebar.jsp"%>

        <c:if test="${not empty alert}">
          <%@include file="/WEB-INF/views/partials/alert.jsp" %>
        </c:if>

        <div class="body grid-y align-center">
          <div class="box">
            <div class="container">
              <h2 style="text-align: center">Crea un nuovo corso</h2>

              <form action="${pageContext.request.contextPath}/user/executeCreateCorso" method="post"
                    class="grid-x justify-center" enctype="multipart/form-data">
                <div class="grid-y cell w80 logging">
                  <label for="nome" class="field">Nome del corso</label>
                    <input type="text" id="nome" name="nome" minlength="5" maxlength="50">
                    <p id="namerr" class="err">Il nome di un corso deve essere lungo 5-30 caratteri
                      e non può contenere caratteri speciali.</p> <br>

                  <label for="prezzo" class="field">Prezzo (in €)</label>
                    <input type="number" min="0" max="100" step=".25" id="prezzo" name="prezzo" value="0">
                    <p id="prezzerr" class="err">Il prezzo deve essere un decimale</p> <br>

                  <label for="content" class="field">Testo del corso</label>
                    <textarea id="content" name="content" rows="20" maxlength="10000"></textarea>
                    <p id="contenterr" class="err">La descrizione è vuota</p> <br>

                  <label for="cover" class="field">Immagine di copertina</label>
                    <input type="file" id="cover" name="cover"> <br>

                  <button type="submit" class="btn primary" onclick="return CorsoValidate()">Invia</button>
                </div>
              </form>
            </div>
          </div>
        </div>

        <%@include file="../partials/home/footer.jsp"%>
      </section>
    </main>
  </body>
</html>