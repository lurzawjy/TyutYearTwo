package com.servlet;

import com.dao.UserDao;
import com.domain.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub
//设置编码格式
        //处理响应的中文乱码
        response.setContentType("text/html;charset=utf-8");
        //处理请求参数的中文乱码问题
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
//获取前端传来参数
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
//创建空 user 对象，填充参数数据并传入 dao 层
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        UserDao userDao = new UserDao();
        boolean flag = false;
        try {
            flag = userDao.insert(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//判断注册是否成功
        if (flag) {
            out.println("注册成功，欢迎 " + name);
        } else {
            out.println("注册失败");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     this.doGet(request,response);
    }
}
