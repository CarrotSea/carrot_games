<%--
  Created by IntelliJ IDEA.
  User: 33735
  Date: 11/21/2021
  Time: 10:55 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h1>Register: </h1>
    <form action="HelloServlet" method="post">
        <input type="hidden" name="action" value="register">
        <label for="id">ID:</label>
        <input id="id" type="text" name="id">
        <label for="password">Password:</label>
        <input id="password" type="password" name="password">
        <input type="submit" value="Submit">
    </form>
    <p>${message}</p>
    <p>If you have already registered, please <a href="log_in.jsp">log in</a></p>
</body>
</html>
