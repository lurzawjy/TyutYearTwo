package com.dao;

import com.domain.User;
import com.utils.DBUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserDao {
    public Boolean isLogin(User user){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            conn = DBUtils.getConnection();
            stmt = conn.createStatement();
            String SQL = "select * from user where name = '"+user.getName()+"'and password = '"+user.getPassword()+"'";
            rs = stmt.executeQuery(SQL);
            if(rs.next())
            {
                return true;
            }
        }catch (Exception e){ e.printStackTrace();}
        finally{
            try{
                DBUtils.release(rs,stmt,conn);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }
    public Boolean insert(User user) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DBUtils.getConnection();
            stmt = conn.createStatement();
            String SQL = "INSERT INTO user(name,password,email)"+"values('"+user.getName()+"','"+user.getPassword()+"','"+user.getEmail()+"')";
            int num = stmt.executeUpdate(SQL);
            if(num>0){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){e.printStackTrace();}
        finally {

            DBUtils.release(stmt,conn);
        }
        return false;
    }
}
