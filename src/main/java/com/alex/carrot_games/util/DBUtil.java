package com.alex.carrot_games.util;

import com.alex.carrot_games.data.RecordDB;

import java.sql.*;
import java.util.ArrayList;

public class DBUtil {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException E) {
            E.printStackTrace();
        }
        String dbURL = "jdbc:mysql://localhost:3306/carrot_games";
        String username = "root";
        String password = "root";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    dbURL, username, password);
            return connection;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public static ArrayList<String> getGames() {
        ArrayList<String> games = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query =  "SELECT TABLE_NAME\n" +
                " FROM INFORMATION_SCHEMA.TABLES\n" +
                " WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='carrot_games' AND\n" +
                " TABLE_NAME != 'users'";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                games.add(rs.getString("TABLE_NAME"));
            }
            return games;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            try {
                connection.close();
                ps.close();
                rs.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static String getHTMLTable(ResultSet results) throws SQLException{
        StringBuilder table = new StringBuilder();
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        table.append("<table>");

        table.append("<tr>");
        for (int i = 1; i <= columnCount; i++) {
            table.append("<th>");
            table.append(metaData.getColumnName(i));
            table.append("</th>");
        }

        table.append("</tr>");

        while (results.next()) {
            table.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                table.append("<td>");
                table.append(results.getString(i));
                table.append("</td>");
            }
            table.append("</tr>");
        }

        table.append("</table>");
        return table.toString();
    }

    public static void main(String[] args) {
        ArrayList<String> games = DBUtil.getGames();
        String ranks = "";
        for (String game: games) {
            ranks += RecordDB.getGameRecords(game);
        }
        System.out.println(ranks);
    }
}
