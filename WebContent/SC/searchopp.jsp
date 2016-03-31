<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.xdf.dto.Channel"%>
<%@page import="com.xdf.dto.Management" %>
<%@page import="com.xdf.dto.User" %>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查询数据</title>
<!-- css文件 -->
<link href="<%=request.getContextPath()%>/css/SCMain.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<!-- js文件 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/SCMain.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	var table = $("#oppTable").DataTable({});
	
	$("#searchButton").click(function(){
		$.ajax({
			url : 'searchOppBySC.action',
			type : 'post',
			dataType : 'json',
			data : {'user' : $("#usernameShow").html().split(":")[1],
					'beginDate' : $("#beginDate").val(),
					'endDate' : $("#endDate").val(),
					'stuContact' : $("#stuContactTel").val()},
			success : function(e){
				table.clear().draw(false);
				var data = eval("(" + e + ")");
				for (var i = 0; i < data.length; i++) {
					var obj = [
								data[i].stuName,
								data[i].parentName,
								data[i].contactTel1,
								data[i].contactTel2,
								data[i].needCls,
								data[i].management,
								data[i].channelName,
								data[i].channelType,
					           	data[i].createDate,
					           	data[i].isValid,
					           	data[i].noValidReason,
					           	data[i].isAssign,
					           	data[i].assignEmployee
					           ];
					table.row.add(obj).draw(false);
				}
			},
			error : function(e){
				alert("网络出错！请联系管理员！")
			}
		})
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
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/addChannel.jsp">添加渠道商</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/dealInfo.jsp">成单数据</a></li>
  				<li role="presentation" class="active"><a href="#">查询数据</a></li>
			</ul>
		</div>
	</div>
</nav>
<br/>
<div style="width: 90%;margin-left: auto;margin-right: auto;" class="well well-lg">
	<form class="form-inline">
		<div class="form-group">
    		<div class="input-group">
      			<div class="input-group-addon">起始时间</div>
      			<input type="text" class="form-control" id="beginDate" placeholder="起始时间" onclick="WdatePicker()">
    		</div>
  		</div>
  		&nbsp;&nbsp;&nbsp;&nbsp;
  		<div class="form-group">
    		<div class="input-group">
      			<div class="input-group-addon">截止时间</div>
      			<input type="text" class="form-control" id="endDate" placeholder="截止时间" onclick="WdatePicker()">
    		</div>
  		</div>
  		&nbsp;&nbsp;&nbsp;&nbsp; 
  		<div class="form-group">
    		<div class="input-group">
      			<div class="input-group-addon">学员电话</div>
      			<input type="text" class="form-control" id="stuContactTel" placeholder="学员电话">
    		</div>
  		</div>
  		<input type="button" class="btn btn-primary" id="searchButton" value="查询" data-loading-text="查询中">
  		<!-- <input type="button" class="btn btn-primary" id="exportExcel" value="导出" data-loading-text="导出中"> -->
	</form>
</div>
<br/>
<div class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">商机数据</div>
	<table id="oppTable" class="table table-striped table-bordered dataTable" style="width: 100%" aria-describedby="example_info" role="grid" cellspacing="0" width="100%">
		<thead>
			<tr> 
				<th>学员姓名</th>
				<th>家长姓名</th>
				<th>联系方式1</th>
				<th>联系方式2</th>
				<th>需求课程</th>
				<th>所属部门</th>
				<th>渠道商</th>
				<th>渠道商类型</th>
				<th>创建日期</th>
				<th>是否有效</th>
				<th>无效原因</th>
				<th>是否分配</th>
				<th>分配员工</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		<tfoot>
			<tr>
				<th>学员姓名</th>
				<th>家长姓名</th>
				<th>联系方式1</th>
				<th>联系方式2</th>
				<th>需求课程</th>
				<th>所属部门</th>
				<th>渠道商</th>
				<th>渠道商类型</th>
				<th>创建日期</th>
				<th>是否有效</th>
				<th>无效原因</th>
				<th>是否分配</th>
				<th>分配员工</th>
			</tr>
		</tfoot>
	</table>
</div>
</body>
</html>