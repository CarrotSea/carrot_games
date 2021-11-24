<%--
  Created by IntelliJ IDEA.
  User: 33735
  Date: 11/21/2021
  Time: 11:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
<h1>Log in: </h1>
<form action="HelloServlet" method="post">
    <input type="hidden" name="action" value="log_in">
    <label for="id">ID:</label>
    <input id="id" type="text" name="id">
    <label for="password">Password:</label>
    <input id="password" type="password" name="password">
    <input type="submit" value="Submit">
</form>
</body>
</html>
