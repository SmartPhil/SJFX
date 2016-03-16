<%@page import="com.xdf.dto.Management"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商机分销系统</title>
<link href="<%=request.getContextPath()%>/css/channelMain.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/table.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/waiting.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/channelMain.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
</head>
<body>
<% 
	HttpSession sessions = request.getSession();
	Object username = sessions.getAttribute("username");
	Object channelname = sessions.getAttribute("channelName");
	Object channeltype = sessions.getAttribute("channelType");
	List<Management> managementList = (List<Management>)sessions.getAttribute("managementList");
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
				<li role="presentation"><a href="#">欢迎您:<%=username %></a></li>
  				<li role="presentation" class="active"><a href="#">录入商机</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/channel/channelDealInfo.jsp">查看成单情况</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/channel/importOpp.jsp">批量导入</a></li>
			</ul>
		</div>
		<div class="navbar-footer"></div>
	</div>
</nav>
<br/>
<div id="body">
<div id="showSjInput">
	<form id="sjForm">
		<div class="form-group">
  			<p class="bg-primary">注意：带*号为必填项</p>	
  		</div>
  		<div class="form-group">
  			<label for="exampleInputEmail1">学员姓名</label>
    		<input type="text" class="form-control" id="stuName" name="stuName" placeholder="请输入学员姓名">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputPassword1">家长姓名</label>
    		<input type="text" class="form-control" id="parentName" name="parentName" placeholder="请输入家长姓名">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">联系方式1<span class="badge">*</span></label>
    		<input type="text" class="form-control" id="contactTel1" name="contactTel1" placeholder="请输入手机号码/座机号码">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">联系方式2</label>
    		<input type="text" class="form-control" id="contactTel2" name="contactTel2" placeholder="请输入手机号码/座机号码">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">咨询课程</label>
    		<textarea rows="3" cols="10" class="form-control" id="consultCls" name="consultCls" placeholder="请输入学员咨询的课程"></textarea>
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">所属客服部门</label>
    		<select class="form-control" id="kfManagement" name="kfManagement">
    			<% for(int i = 0; i < managementList.size(); i++){ %>
						<option value="<%=managementList.get(i).getManagementName() %>"><%=managementList.get(i).getManagementName() %></option>
				<%	} %>
    		</select>
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">提供机构</label>
    		<input type="text" class="form-control" value="<%=channelname %>" id="giveOrg" name="giveOrg" readonly="readonly"/>
  		</div>
		<div class="form-group">
    		<label for="exampleInputFile">渠道类型</label>
    		<input type="text" class="form-control" value="<%=channeltype %>" id="channelType" name="channelType" readonly="readonly"/>
  		</div>
  		<input type="button" data-loading-text="提交中……" class="btn btn-primary" id="submitSJ" value="提交"/>
	</form>
</div>
</div>
<div id="foot">
	
</div>
</body>
</html>