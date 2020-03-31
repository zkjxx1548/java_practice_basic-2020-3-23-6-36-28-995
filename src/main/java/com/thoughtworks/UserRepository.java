package com.thoughtworks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public void save(User user) {
        String sql = "INSERT INTO user(name, telephone, email, password)"
                + "values(" + "?,?,?,?)";
        Connection conn;
        PreparedStatement pt;
        try {
            conn = DbUtil.getConnection();
            pt = conn.prepareStatement(sql);

            pt.setString(1, user.getName());
            pt.setString(2, user.getTelephone());
            pt.setString(3, user.getEmail());
            pt.setString(4, user.getPassword());

            pt.executeUpdate();
            pt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User queryByName(String name) {
        String sql = "SELECT name, telephone, email, password, login_count, login_lock FROM user " +
                "WHERE name = ?";
        Connection conn;
        PreparedStatement pt;
        ResultSet rs;
        User user = null;
        try {
            conn = DbUtil.getConnection();
            pt = conn.prepareStatement(sql);
            pt.setString(1, name);
            rs = pt.executeQuery();
            while (rs.next()) {
                user = new User(
                        rs.getString("name"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("login_count"),
                        rs.getBoolean("login_lock")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        closeJDBC(rs, pt, conn);
        return user;
    }

    public void updateLoginCount(String name, int loginCount) {
        String sql = "UPDATE user SET login_count = ? WHERE name = ?";
        Connection conn;
        PreparedStatement pt;
        try {
            conn = DbUtil.getConnection();
            pt = conn.prepareStatement(sql);

            pt.setInt(1, loginCount);
            pt.setString(2, name);
            pt.executeUpdate();
            pt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateLoginLock(String name, boolean b) {
        String sql = "UPDATE user SET login_lock = ? WHERE name = ?";
        Connection conn;
        PreparedStatement pt;
        try {
            conn = DbUtil.getConnection();
            pt = conn.prepareStatement(sql);

            pt.setBoolean(1, b);
            pt.setString(2, name);
            pt.executeUpdate();
            pt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeJDBC(ResultSet rs, PreparedStatement ps, Connection conn) {
        try{
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        try {
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
