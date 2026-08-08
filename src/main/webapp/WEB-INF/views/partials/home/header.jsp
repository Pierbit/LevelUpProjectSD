<%@ page import="model.utente.Utente" %>
<header class="grid-x align-center menu">
    <div class="links grid-x">
        <div><a href="${pageContext.request.contextPath}/">Home</a></div>
        <div><a class="sidebar-switch">Esplora</a></div>
        <% if (session.getAttribute("utente") != null) { %>
            <div><a href="${pageContext.request.contextPath}/home/publish">Pubblica</a></div>
        <% } else { %>
            <div><a href="${pageContext.request.contextPath}/accounts/login">Pubblica</a></div>
        <% } %>
    </div>

    <div class="usr grid-x">
        <%
            if (session.getAttribute("utente") != null) {
        %>
            <div class="dropdown">
                <a>
                    <%@include file="/icons/user.svg"%>
                </a>

                <div class="dropdown-content">
                    <%
                        if (session.getAttribute("utente") != null && ((Utente) session.getAttribute("utente")).getManager()) {
                    %>
                        <p><a href="${pageContext.request.contextPath}/manager/goindex">Area amministrazione</a></p>
                    <%
                        }
                    %>

                    <p>
                        <a href="${pageContext.request.contextPath}/user/profile?nickname=<%=((Utente) session.getAttribute("utente")).getNickname()%>">
                            Profilo
                        </a>
                    </p>
                    <p><a href="${pageContext.request.contextPath}/user/showAllOrders">Visualizza ordini</a></p>
                    <p><a href="${pageContext.request.contextPath}/user/updateUtente">Modifica dati</a></p>
                    <p><a href="${pageContext.request.contextPath}/accounts/logout">Logout</a></p>
                </div>
            </div>
        <%
            } else {
        %>
            <div><a href="${pageContext.request.contextPath}/accounts/login"><%@include file="/icons/user.svg"%></a></div>
        <%
            }
        %>
        <div><a href="${pageContext.request.contextPath}/home/visualizzaCarrello"><%@include file="/icons/shopping-cart.svg"%></a></div>
    </div>
</header>

