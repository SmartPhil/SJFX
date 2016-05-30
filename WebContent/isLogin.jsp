<%@page import="org.apache.catalina.deploy.LoginConfig"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>isLogin</title>
</head>
<body>
<%
	PrintWriter printWriter = response.getWriter();
	try{
		String username = session.getAttribute("username").toString();
		
		if("".equals(username) || username == null){
			printWriter.print("<script>alert(\"请先登录！\")</script>");
			printWriter.print("<script>window.location.href = \"../login.jsp\"</script>");
		}
	}catch (Exception e) {
		printWriter.print("<script>alert(\"请先登录！\")</script>");
		printWriter.print("<script>window.location.href = \"../login.jsp\"</script>");
	}
%>
</body>
</html>