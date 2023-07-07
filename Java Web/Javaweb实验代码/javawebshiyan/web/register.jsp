<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2023/6/2
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
</head>
<body>
<form action="/javaweb/RegisterServlet" method="post" >
    用户名：<input type="text" name="name" /><br/>
    密 码：<input type="password" name="password" /><br/>
    邮 箱：<input type="text" name="email" /><br/>
    <input type="submit" value="注册" />
</form>
</body>
</html>