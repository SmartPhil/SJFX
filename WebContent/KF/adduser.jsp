<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.xdf.dto.Management"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加用户</title>
<link href="<%=request.getContextPath()%>/bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap-3.3.4-dist/js/bootstrap.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#submitAddKFUser").click(function(){
		var username = $("#kfUsername").val();
		var password = $("#kfPassword").val();
		var confirmPassword = $("#kfConfirmPassword").val();
		var name = $("#kfName").val();
		var management = $("#kfManagement").val();
		if(username == null || username == ""){
			alert("用户名不能为空！");
			return;
		}
		if(password == null || password == ""){
			alert("密码不能为空！");
			return;
		}
		if(confirmPassword == null || confirmPassword == ""){
			alert("确认密码不能为空！");
			return;
		}
		
		$.ajax({
			url : "addKFEmployeeUser.action",
			type : "post",
			data : {"username":username,"password":password,"name":name,"management":management},
			dataType : "json",
			success : function(e){
				var data = eval("(" + e + ")");
				var result = data.result;
				if(result == "success"){
					alert("添加成功！");
					window.location.reload();
				}else{
					alert("添加失败！");
				}
			},
			error : function(e){
				alert("添加失败！");
			}
		})
	});
	
	$("#kfPassword").blur(function(){
		var psw = $("#kfPassword").val();
		if(psw == null || psw == ""){
			$("#pswNotNullAlert").css("display","block");
		}else{
			$("#pswNotNullAlert").css("display","none");
		}
		if(canSubmit()){
			$("#submitAddKFUser").removeAttr("disabled");
		}else{
			$("#submitAddKFUser").attr("disabled","disabled");
		}
	});
	
	$("#kfConfirmPassword").blur(function(){
		var psw = $("#kfPassword").val();
		var confirmPsw = $("#kfConfirmPassword").val();
		if(psw != confirmPsw){
			$("#confirmPswAlert").css("display","block");
			//没有聚焦过去？
			$("#kfConfirmPassword").focus();
		}else{
			$("#confirmPswAlert").css("display","none");
		}
		if(canSubmit()){
			$("#submitAddKFUser").removeAttr("disabled");
		}else{
			$("#submitAddKFUser").attr("disabled","disabled");
		}
	});
	
	$("#kfUsername").blur(function(){
		var username = $("#kfUsername").val();
		if(username == null || username == ""){
			$("#usernameNotNull").css("display","block");
		}else{
			$("#usernameNotNull").css("display","none");
		}
		
		$.ajax({
			url : "userNameIsExist.action",
			type : "post",
			data : {"username":username},
			dataType : "json",
			success : function(e){
				var data = eval("(" + e + ")");
				var result = data.result; 
				if(result == "isExist"){
					$("#usernameIsExist").css("display","block");
				}else if(result == "notExist"){
					$("#usernameIsExist").css("display","none");
				}
			},
			error : function(e){
				alert("网络出错！请检查网络或者联系管理员！");
			}
		});
		if(canSubmit()){
			$("#submitAddKFUser").removeAttr("disabled");
		}else{
			$("#submitAddKFUser").attr("disabled","disabled");
		}
	});
	
	function canSubmit(){
		var username = $("#kfUsername").val();
		var password = $("#kfPassword").val();
		var confirmPassword = $("#kfConfirmPassword").val();
		var result1 = false;
		if(username != null && username != "" && password != null && password != "" && confirmPassword != null && confirmPassword != ""){
			result1 = true;
		}else{
			result1 = false;
		}
		var result2 = false;
		var namedis = ($("#usernameNotNull").css("display") == "none" && $("#usernameIsExist").css("display") == "none");
		var pswdis = ($("#pswNotNullAlert").css("display") == "none");
		var confirmPswDis = ($("#confirmPswAlert").css("display") == "none");
		if(namedis && pswdis && confirmPswDis){
			result2 = true;
		}
		if(result1 && result2){
			return true;
		}else{
			return false;
		}
	}
});
</script>
</head>
<body>
<% 
	HttpSession sessions = request.getSession();
	Object username = sessions.getAttribute("username");
	String management = (String)sessions.getAttribute("management");
	String[] managements = management.split(",");
%>
<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">
        		<img alt="" src="<%=request.getContextPath()%>/image/logo.png">
      		</a>
		</div>
		<div class="collapse navbar-collapse" style="margin-left: auto;margin-right: auto;width: 70%;">
			<ul class="nav nav-pills">
				<li role="presentation"><a id="usernameShow" href="#">欢迎您:<%=username %></a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/toKF.action">未处理商机</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/tj.jsp">数据量统计</a></li>
  				<li role="presentation" class="active"><a href="#">添加用户</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/queryOpp.jsp">查询数据</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/dealInfo.jsp">成单数据</a></li>
  				<li role="presentation"><a href="#">导入商机</a></li>
			</ul>
		</div>
	</div>
</nav>
<div class="panel panel-primary" style="width: 50%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">添加用户</div>
	<br>
	<form id="sjForm">
  		<div class="form-group">
  			<label for="exampleInputEmail1">用户名</label>
    		<input type="text" class="form-control" id="kfUsername" name="kfUsername" placeholder="请输入用户名">
    		<div id="usernameNotNull" style="display: none;" class="alert alert-danger" role="alert">
    			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 用户名不能为空！
    		</div>
    		<div id="usernameIsExist" style="display: none;" class="alert alert-danger" role="alert">
    			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 用户名已存在！
    		</div>
  		</div>
  		<div class="form-group">
    		<label for="exampleInputPassword1">密码</label>
    		<input type="password" class="form-control" id="kfPassword" name="kfPassword" placeholder="请输入密码">
    		<div id="pswNotNullAlert" style="display: none;" class="alert alert-danger" role="alert">
    			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 密码不能为空！
    		</div>
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">确认密码</label>
    		<input type="password" class="form-control" id="kfConfirmPassword" name="kfConfirmPassword" placeholder="请再输入一次密码">
    		<div id="confirmPswAlert" style="display: none;" class="alert alert-danger" role="alert">
    			<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 确认密码与密码不一致！
    		</div>
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">姓名(可为空)</label>
    		<input type="text" class="form-control" id="kfName" name="kfName" placeholder="请输入姓名">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">管理部门</label>
    		<select class="form-control" id="kfManagement" name="kfManagement">
    		<% for(int i = 0; i < managements.length; i++){%>
    			<option value="<%=managements[i] %>"><%=managements[i] %></option>
    		<% }%>
    		</select>
  		</div>
  		<input type="button" data-loading-text="添加中……" class="btn btn-primary" id="submitAddKFUser" value="添加" disabled="disabled"/>
	</form>
</div>
</body>
</html>