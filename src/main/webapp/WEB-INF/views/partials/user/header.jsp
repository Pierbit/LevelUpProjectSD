<%@ page import="model.utente.Utente" %>
<header class="grid-x align-center menu">
    <span class="links">
        <span><a href="${pageContext.request.contextPath}/">Home</a></span>
        <span><a class="sidebar-switch">Esplora</a></span>
        <span><a href="${pageContext.request.contextPath}/home/publish">Pubblica</a></span>

        <%
            if (session.getAttribute("utente") != null) {
        %>
            <span><a href="${pageContext.request.contextPath}/user/showAllOrders">Visualizza ordini</a></span>
            <span><a href="${pageContext.request.contextPath}/user/updateUtente">Modifica dati</a></span>
            <span><a href="${pageContext.request.contextPath}/accounts/logout">Logout</a></span>
        <%
            }
        %>

        <%
            if (session.getAttribute("utente") != null && ((Utente) session.getAttribute("utente")).getManager()) {
        %>
            <span><a href="${pageContext.request.contextPath}/manager/goindex">Area amministrazione</a></span>
        <%
            }
        %>
    </span>

    <span class="usr">
        <%
            if (session.getAttribute("utente") != null) {
        %>
            <span>
                <a href="${pageContext.request.contextPath}/user/profile?nickname=<%=((Utente) session.getAttribute("utente")).getNickname()%>">
                    <%@include file="/icons/user.svg"%>
                </a>
            </span>
        <%
        } else {
        %>
            <span><a href="${pageContext.request.contextPath}/accounts/login"><%@include file="/icons/user.svg"%></a></span>
        <%
            }
        %>
        <span><a href="${pageContext.request.contextPath}/home/cart"><%@include file="/icons/shopping-cart.svg"%></a></span>
    </span>
</header>
