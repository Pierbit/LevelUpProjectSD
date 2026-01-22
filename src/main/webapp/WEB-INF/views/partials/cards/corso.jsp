<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="card">
    <a href="${pageContext.request.contextPath}/course/view?id=${param.id}">
        <img src="${pageContext.request.contextPath}/covers/${param.cover}" alt="card" style="width:100%">
        <div class="container">
            <h4><b>${param.title}</b></h4>
            <p>${param.user}</p>
            <h3>${param.price}€</h3>
        </div>
    </a>
</div>