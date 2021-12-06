package com.alex.carrot_games.data;

import com.alex.carrot_games.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;

public class RecordDB {

    public static boolean containsRecord(String id, String game) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT id FROM " + getQueryString(game) + " WHERE id = ?";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println(e);
            return false;
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

    public static void insertRecord(String id, String game, int record) {
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO " + getQueryString(game) + " (id, score) "
                + "VALUES (?, ?)";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ps.setInt(2, record);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                connection.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static int getRecord(String id, String game) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT id, score FROM " + getQueryString(game) +
                " WHERE id = ?";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            int record = 0;
            if (rs.next()) {
                record = rs.getInt("score");
            }
            return record;
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
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

    public static String getQueryString(String game) {
        String result = "";
        switch (game) {
            case "snake":
                result = "snake";
                break;
            case "minesweeper_beginner":
                result = "minesweeper_beginner";
                break;
            case "minesweeper_intermediate":
                result = "minesweeper_intermediate";
                break;
            case "minesweeper_expert":
                result = "minesweeper_expert";
                break;
        }
        return result;
    }

    public static void updateRecord(String id, String game, int record) {
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "UPDATE " + getQueryString(game) + " SET score = ? "
                + "WHERE id = ?";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            ps.setInt(1, record);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                connection.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static void removeRecord(String id, String game) {
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "DELETE FROM " + getQueryString(game) + " WHERE id = ?";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                connection.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public static String getGameRecords(String game) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM " + getQueryString(game);
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            return DBUtil.getHTMLTable(rs);
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

    public static void main(String[] args) {
        /*
        String id = "alex";
        String game = "minesweeper_intermediate";
        int record = 30;
        System.out.println(containsRecord(id, game));
        insertRecord(id, game, record);
        System.out.println(getRecord(id, game));
        System.out.println(containsRecord(id ,game));
        updateRecord(id, game, 40);
        System.out.println(getRecord(id, game));
        removeRecord(id, game);
        System.out.println(containsRecord(id,game));
        System.out.println(getRecord(id, game));
        for (String s: getGames()) {
                    removeRecord("alex", s);
                    removeRecord("arthur", s);
                    removeRecord("lucy", s);
                }
        */
    }
}
