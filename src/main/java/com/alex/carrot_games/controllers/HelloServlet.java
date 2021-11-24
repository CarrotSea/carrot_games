package com.alex.carrot_games.controllers;

import com.alex.carrot_games.business.User;
import com.alex.carrot_games.util.CookieUtil;
import com.alex.carrot_games.data.UserDB;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/HelloServlet")
@MultipartConfig
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "";
        if (action.equals("game")) {
            url = checkUser(request, response);
        } else if (action.equals("rank")) {
            // codes for fetching ranking data and appending the data to request
            url = "/rank.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String url = "/index.jsp";
        if (action.equals("register")) {
            url = registerUser(request, response);
        } else if (action.equals("score")) {

        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    private String checkUser(HttpServletRequest request, HttpServletResponse response) {
        String game = request.getParameter("game");
        HttpSession session = request.getSession();
        session.setAttribute("game", game);
        User user = (User)session.getAttribute("user");

        String url = "";
        if (user == null) {
            Cookie[] cookies = request.getCookies();
            String id = CookieUtil.getCookieValue(cookies, "idCookie");
            if (id == null || id.equals("")) {
                url = "/register.jsp";
            } else {
                user = UserDB.getUser(id);
                session.setAttribute("user", user);
                url = checkGame(game);
            }
        } else {
            url = checkGame(game);
        }
        return url;
    }

    private String checkGame(String game) {
        String url = "";
        switch (game) {
            case "minesweeper_beginner":
                url = "/minesweeper_beginner.html";
                break;
            case "minesweeper_intermediate":
                url = "/minesweeper_intermediate.html";
                break;
            case "minesweeper_expert":
                url = "/minesweeper_expert.html";
                break;
            case "snake":
                url = "/snake.html";
                break;
        }
        return url;
    }

    private String registerUser(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        String url = "";
        if (!UserDB.containsUser(id)) {
            User user = new User();
            user.setId(id);
            user.setPassword(password);

            UserDB.addUser(user);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            Cookie c = new Cookie("idCookie", id);
            c.setMaxAge(60 * 60 * 24 * 365);
            c.setPath("/");
            response.addCookie(c);

            String game = (String) session.getAttribute("game");
            url = checkGame(game);
        } else {
            String message = "This ID has already been registered!";
            request.setAttribute("message", message);
            url = "/register.jsp";
        }
        return url;
    }
}
