package com.alex.carrot_games.data;
import com.alex.carrot_games.business.User;
import com.alex.carrot_games.util.DBUtil;

import java.sql.*;

public class UserDB {
    public static void addUser(User user) {
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO Users (id, passwd) "
                        + "VALUES (?, ?)";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getId());
            ps.setString(2, user.getPassword());
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

    public static User getUser(String id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT id, passwd FROM users"
                        + " WHERE id = ?";
        try {
            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setPassword(rs.getString("passwd"));
            }
            return user;
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

    public static boolean containsUser(String id) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT id FROM users WHERE id = ?";
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

    public static void removeUser(String id) {
        Connection connection = null;
        PreparedStatement ps = null;
        String query = "DELETE FROM users WHERE id = ?";
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

    public static void main(String[] args) {
        User user = new User("qifang", "12345yxlm");
        addUser(user);
        System.out.println(containsUser("qifang"));
        System.out.println(getUser("qifang").getPassword());
        removeUser("qifang");
        System.out.println(containsUser("qifang"));
    }
}
