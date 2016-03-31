<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加渠道商</title>
<!-- css文件 -->
<link href="<%=request.getContextPath()%>/css/SCMain.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<!-- js文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#submitAdd").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		var confirmPassword = $("#confirmPassword").val();
		var channelName = $("#channelName").val();
		var channelType = $("#collaType").val();
		var creator = $("#usernameShow").text().split(":")[1];
		
		if(username == "" || username == null){
			alert("请输入用户名！");
			return;
		}
		if(password == "" || password == null){
			alert("请输入密码！");
			return;
		}
		if(confirmPassword == "" || confirmPassword == null){
			alert("请输入确认密码！");
			return;
		}
		if(channelName == "" || channelName == null){
			alert("请输入渠道商名！");
			return;
		}
		if(password != confirmPassword){
			alert("密码两次输入不一致！");
			return;
		}
		
		$.ajax({
			url : "addChannelUser.action",
			type : "post",
			data : {"username" : username,
					"password" : password,
					"channelName" : channelName,
					"channelType" : channelType,
					"creator" : creator},
			success : function(e){
				var result = eval("(" + e + ")");
				var addResult = result.result;
				if(addResult == "success"){
					alert("添加成功！");
					location.reload();
				}else{
					alert("添加失败！");
				}
			},
			error : function(e){
				alert("添加失败！");
			}
		});
	});
	
	$("#username").blur(function(){
		var username = $("#username").val();
		$.ajax({
			url : "userNameIsExist.action",
			type : "post",
			data : {"username":username},
			dataType : "json",
			success : function(e){
				var data = eval("(" + e + ")");
				var result = data.result; 
				if(result == "isExist"){
					alert("用户名已存在！");
				}
			},
			error : function(e){
				alert("网络出错！请检查网络或者联系管理员！");
			}
		});
	});
});
</script>
</head>
<body>
<% 
	HttpSession sessions = request.getSession();
	Object username = sessions.getAttribute("username");
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
  				<li role="presentation"><a href="<%=request.getContextPath()%>/toSC.action">数据量统计</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/importOpp.jsp">导入商机</a></li>
  				<li role="presentation" class="active"><a href="#">添加渠道商</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/dealInfo.jsp">成单数据</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/searchopp.jsp">自定义返点</a></li>
			</ul>
		</div>
	</div>
</nav>

<div class="panel panel-primary" style="width: 50%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">添加渠道商</div>
	<br>
	<form id="addChannelUserForm">
  		<div class="form-group">
  			<label for="exampleInputEmail1">用户名</label>
    		<input type="text" class="form-control" id="username" name="username" placeholder="请输入用户名">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputPassword1">密码</label>
    		<input type="password" class="form-control" id="password" name="password" placeholder="请输入密码">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">确认密码</label>
    		<input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="请再输入一次密码">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">渠道商名</label>
    		<input type="text" class="form-control" id="channelName" name="channelName" placeholder="请输入渠道商名字">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">渠道类型</label>
    		<select class="form-control" name="collaType">
    			<option value="1" selected="selected">网络渠道</option>
				<option value="2">数据营销</option>
				<option value="3">资源置换</option>
				<option value="4">线下渠道</option>
				<option value="5">线上预约</option>
				<option value="6">地推收集</option>
				<option value="7">活动收集</option>
				<option value="8">校园代理</option>
    		</select>
  		</div>
  		<input type="button" data-loading-text="添加中……" class="btn btn-primary" id="submitAdd" value="添加"/>
	</form>
</div>
<!-- <div id="addChannelUserDiv">
	<form id="addChannelUserForm" action="">
	<table>
		<tr>
			<td>用户名：</td>
			<td><input type="text" id="username" name="username"/></td>
		</tr>
		<tr>
			<td>密码：</td>
			<td><input type="password" id="password" name="password"/></td>
		</tr>
		<tr>
			<td>确认密码：</td>
			<td><input type="password" id="confirmPassword" name="confirmPassword"/></td>
		</tr>
		<tr>
			<td>渠道商名：</td>
			<td><input type="text" id="channelName" name="channelName"/></td>
		</tr>
		<tr>
			<td>合作类型：</td>
			<td>
				<select name="collaType">
					<option value="1" selected="selected">数据合作</option>
					<option value="2">网络合作</option>
					<option value="3">市场推荐</option>
				</select>
			</td>
		</tr>
		<tr>
			<td><button id="submitAdd">确认添加</button></td>
		</tr>
	</table>
	</form>
</div> -->
</body>
</html>