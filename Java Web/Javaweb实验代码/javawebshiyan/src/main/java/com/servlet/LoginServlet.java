package com.servlet;


import com.dao.UserDao;
import com.domain.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        UserDao userDao = new UserDao();
        out.println("欢迎："+ name +"登录");
        Boolean flag = userDao.isLogin(user);
        if(flag){
            out.println("欢迎："+ name +"登录");
        }else{
            request.setAttribute("errorMsg","该用户不存在");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
   }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

