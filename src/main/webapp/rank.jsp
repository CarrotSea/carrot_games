<%--
  Created by IntelliJ IDEA.
  User: 33735
  Date: 11/23/2021
  Time: 12:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="styles/rank.css">
</head>
<body>
<header>
    <h1>Ranks:</h1>
</header>
<section>
    <c:forEach var="entry" items="${ranks}">
        <div class="game_rank">
            <h2>${entry.key}</h2>
                ${entry.value}
        </div>
    </c:forEach>
</section>
</body>
</html>
