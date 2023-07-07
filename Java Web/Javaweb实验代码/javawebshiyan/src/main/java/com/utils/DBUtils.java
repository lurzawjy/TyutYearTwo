package com.utils;
import java.sql.*;
public class DBUtils {
    //  加载驱动，并建立数据库连接
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/student";
        String username = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url, username, password);
        return conn;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = DBUtils.getConnection();
        System.out.println(connection);
    }
    //释放数据库连接
    public static void release(ResultSet rs, Statement stmt, Connection conn) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        rs = null;
        release(stmt, conn);
    }
    public static void release(Statement stmt, Connection conn) throws SQLException {
        if (stmt != null) {
            stmt.close();
        }
        stmt = null;
        if (conn != null) {
            conn.close();
        }
        conn = null;
    }

}
