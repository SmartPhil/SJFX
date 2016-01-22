<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<title>上海新东方商机分销系统</title>
<base href="<%=basePath%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/loginCSS.css" rel="stylesheet" type="text/css"/>
<link href="css/waiting.css" rel="stylesheet" type="text/css"/>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="js/loginJS.js"></script>
<script type="text/javascript" src="js/bootstrap.js"></script>
</head>
<body>
<s:form id="loginForm">
	<div class="loginDiv">
		<div class="panel panel-primary">
			<div class="panel-heading">新东方商机分销系统</div>
  			<div class="panel-body">
    			<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">用户名</span>
  					<input type="text" class="form-control" placeholder="请输入用户名" aria-describedby="basic-addon1" name="username" id="username">
				</div>
				<br>
				<div class="input-group">
  					<span class="input-group-addon" id="basic-addon1">密&nbsp;&nbsp;&nbsp;码</span>
  					<input type="password" class="form-control" placeholder="请输入密码" aria-describedby="basic-addon1" name="password" id="password">
				</div>		
  			</div>
  			<div class="panel-footer">
  				<div class="row">
  					<div class="col-md-6">
  						<input type="reset" class="btn btn-danger" value="重置" id="reset">
  					</div>
  					<div class="col-md-6" style="text-align: right;">
  						<input type="button" class="btn btn-primary" value="登陆" id="btnLogin" title="点击登陆">
  					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
</body>
</html>