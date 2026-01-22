<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,viewport-fit=cover">
<title> ${param.title} </title>
<meta name="description" content="Supera i tuoi limiti con levelUp!">
<link rel="icon" type="image/png" href="${context}/images/logo.png">

<!-- Tag per la corretta visualizzazione su iOS -->
<meta name="format-detection" content="telephone-no"> <!-- Evita che le stringhe numeriche vengano interpretate
    come numeri di telefono -->
<meta name="apple-mobile-web-app-capable" content="yes"> <!-- Abilita il salvataggio del sito come webapp -->
<meta name="apple-mobile-web-app-title" content="levelUp"> <!-- Nome del sito se salvato come webapp -->
<meta name="apple-mobile-web-app-status-bar-style" content="default"> <!-- Colore della barra di stato -->
<link rel="apple-touch-icon" href="${context}/images/logo.png"> <!-- Icona nella barra dei preferiti di iOS -->
<link rel="apple-touch-startup-image" href="${context}/images/logo.png"> <!-- Icona schermata di caricamento -->

<!-- Tag per la corretta visualizzazione du Android -->
<meta name="theme-color" content="#000000"> <!-- Colore del sito -->

<!-- Contenuti di default -->
<link href="${context}/css/reset.css" rel="stylesheet">
<link href="${context}/css/library.css" rel="stylesheet">
<c:if test="${not empty param.styles}">
    <c:forEach items="${param.styles}" var="style">
        <link rel="stylesheet" href="${context}/css/${style}">
    </c:forEach>
</c:if>

<script src="${context}/js/library.js" defer></script>
<c:if test="${not empty param.scripts}">
    <c:forEach items="${param.scripts}" var="script">
        <script src="${context}/js/${script}" defer></script>
    </c:forEach>
</c:if>