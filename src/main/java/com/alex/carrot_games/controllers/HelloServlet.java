package com.alex.carrot_games.controllers;

import com.alex.carrot_games.business.User;
import com.alex.carrot_games.data.RecordDB;
import com.alex.carrot_games.util.CookieUtil;
import com.alex.carrot_games.data.UserDB;
import com.alex.carrot_games.util.DBUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
            ArrayList<String> games = DBUtil.getGames();
            HashMap<String, String> ranks = new HashMap<>();
            for (String game: games) {
                ranks.put(game, RecordDB.getGameRecords(game));
            }
            request.setAttribute("ranks", ranks);
            url = "/rank.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String url = "/index.jsp";
        if (action.equals("log_in")){
            url = logInUser(request, response);
        } else if (action.equals("register")) {
            url = registerUser(request, response);
        } else if (action.equals("score")) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String id = user.getId();
            String game = request.getParameter("game");
            int record = Integer.parseInt(request.getParameter("data"));

            if (RecordDB.containsRecord(id, game)) {
                int oldRecord = RecordDB.getRecord(id, game);
                if (game.equals("snake")) {
                    if (record > oldRecord) {
                        RecordDB.updateRecord(id, game, record);
                    }
                } else if (game.equals("minesweeper_beginner") || game.equals("minesweeper_intermediate") || game.equals("minesweeper_expert")) {
                    if (record < oldRecord) {
                        RecordDB.updateRecord(id, game, record);
                    }
                }
            } else {
                RecordDB.insertRecord(id, game, record);
            }
            url = checkGame(game);
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

    private String logInUser(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String url = "";
        String message = "";

        if (!UserDB.containsUser(id)) {
            message = "Id does not exist. Please register before you log in.";
            request.setAttribute("message", message);
            url = "/log_in.jsp";
        } else {
            User user = UserDB.getUser(id);
            if (!password.equals(user.getPassword())) {
                message = "Password does not match.";
                request.setAttribute("message", message);
                url = "/log_in.jsp";
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                Cookie c = new Cookie("idCookie", id);
                c.setMaxAge(60 * 60 * 24 * 365);
                c.setPath("/");
                response.addCookie(c);
                url = "/index.jsp";
            }
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
