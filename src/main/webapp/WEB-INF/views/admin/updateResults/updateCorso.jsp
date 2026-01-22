<%@ page import="model.corso.Corso" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <jsp:include page="../../partials/head.jsp">
      <jsp:param name="title" value="Modifica un corso esistente"/>
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

        <%
          Corso corso = (Corso) request.getAttribute("corso");
        %>

        <div class="body grid-y align-center">
          <div class="box">
            <div class="container">
              <h2 style="text-align: center">Aggiorna il corso selezionato</h2>

              <form action="${pageContext.request.contextPath}/manager/executeUpdateCorso" method="post"
                    class="grid-x justify-center" enctype="multipart/form-data">
                <div class="grid-y cell w80 logging">
                  <label for="nome" class="field">Nome del corso</label>
                  <input type="text" minlength="5" maxlength="50" id="nome" name="nome" value="<%=corso.getNome()%>">
                  <p id="namerr" class="err">Il nome di un corso deve essere lungo 5-30 caratteri
                    e non può contenere caratteri speciali.</p>
                  <label for="prezzo" class="field">Prezzo di base</label>
                  <input type="number" step=".25" id="prezzo" name="prezzo" value="<%=corso.getPrezzoBase()%>">
                  <p id="prezzerr" class="err">Il prezzo deve essere un decimale</p>
                  <label for="content" class="field">Contenuto</label>
                  <textarea id="content" rows="10" maxlength="10000" name="content"><%=corso.getTesto()%></textarea>
                  <p id="contenterr" class="err">La descrizione è vuota</p>
                  <label for="cover" class="field">Immagine di copertina</label>
                  <input type="file"
                         accept=".apng, .avif, .gif, .jpg, .jpeg, .jfif, .pjpeg, .pjp, .png, .svg, .webp"
                         id="cover" name="cover">
                  <input type="hidden" name="corsoid" value="${corso.id}">
                  <button type="submit" class="btn primary" onclick="return CorsoValidate()">Aggiorna</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </section>
    </main>
  </body>
</html>
