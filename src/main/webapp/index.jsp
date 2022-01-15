<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" href="styles/index.css">
</head>
<body>
<header>
    <h1>Welcome to Carrot Games!</h1>
    <p>This is a gallery for some funny little games I've written with HTML, CSS, and JavaScript. </p>
    <p><a href="log_in.jsp">Log in</a> to compete with others by submitting your records!</p>
    <p><a href="register.jsp">Register</a> if you do not have an account yet.</p>
    <p>Check your <a href="HelloServlet?action=rank">ranks</a> here.</p>
    <p>Have fun!</p>
</header>
<section id="gallery">
    <div id="mine_sweeper_beginner">
        <a href="HelloServlet?action=game&game=minesweeper_beginner">
            <img src="./images/minesweeper_beginner.png" alt="Mine Sweeper(Beginner)" width="100" height="100">
            <p class="description">Mine Sweeper(Beginner)</p>
        </a>
    </div>
    <div id="mine_sweeper_intermediate">
        <a href="HelloServlet?action=game&game=minesweeper_intermediate">
            <img src="./images/minesweeper_intermediate.png" alt="Mine Sweeper(Intermediate)" width="100" height="100">
            <p class="description">Mine Sweeper(Intermediate)</p>
        </a>
    </div>
    <div id="mine_sweeper_expert">
        <a href="HelloServlet?action=game&game=minesweeper_expert">
            <img src="./images/minesweeper_expert.png" alt="Mine Sweeper(Expert)" width="100" height="100">
            <p class="description">Mine Sweeper(Expert)</p>
        </a>
    </div>
    <div id="snake">
        <a href="HelloServlet?action=game&game=snake">
            <img src="./images/snake.png" alt="Snake" width="100" height="100">
            <p class="description">Snake</p>
        </a>
    </div>
</section>
</main>
</body>
</html>