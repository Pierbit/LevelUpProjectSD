<div class="notification ${alert.type}">
    <ol class="cell">
        <c:forEach var="msg" items="${alert.messages}">
            <li>
                ${msg}
            </li>
        </c:forEach>
    </ol>
    <span id="notification-close" class="close">
        <%@include file="/icons/delete.svg"%>
    </span>
</div>

<script>
    deleteicon = document.getElementById("notification-close");
    deleteicon.addEventListener('click', function () {

        const navbar = document.getElementsByClassName("notification")[0];
        navbar.classList.toggle("hide");

    })
</script>