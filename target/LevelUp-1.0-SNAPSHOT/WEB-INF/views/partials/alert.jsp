
<div class="notification ${alert.type}">
    <ol class="cell">
        <c:forEach var="msg" items="${alert.messages}">
            <li>
                ${msg}
            </li>
        </c:forEach>
    </ol>
    <span id="notification-close" class="close">
        <%@include file="/icons/th-menu.svg"%>
    </span>
</div>