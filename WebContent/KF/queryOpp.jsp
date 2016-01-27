<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商机分销系统</title>
<link href="bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="bootstrap-3.3.4-dist/js/bootstrap.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//获取当月商机数据
	var t = $("#oppInfoTab").DataTable({});
	t.clear().draw(false);
	$.ajax({
		url : "getCurrentMonthOpp.action",
		type : "post",
		dataType : "json",
		success : function(data){
			var result = eval("(" + data + ")");
			for(var i = 0; i < result.length; i++){
				var obj = [
				           result[i].stuName,result[i].parentName,result[i].contactTel1,result[i].contactTel2,
				           result[i].needCls,result[i].management,result[i].channelName,result[i].channelType,
				           result[i].createDate,result[i].isValid,result[i].noValidReason,result[i].isAssign,
				           result[i].assignEmployee
				          ];
				
				t.row.add(obj).draw(false);
			}
		},
		error : function(){
			alert("Net Error!");
		}
	});
	
	$("#searchButton").click(function(){
		var beginDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		var stuContact = $("#stuContactTel").val();
		var user = $("#usernameShow").text().split(":")[1];			
		$.ajax({
			url : "searchOpp.action",
			type : "post",
			data : {"user" : user, "beginDate" : beginDate, "endDate" : endDate, "stuContact" : stuContact},
			success : function(data){
				t.clear().draw(false);
				var result = eval("(" + data + ")");
				for(var i = 0; i < result.length; i++){
					var obj = [
					           	result[i].name,result[i].parentName,result[i].contactTel1,
					           	result[i].contactTel2,result[i].needCls,result[i].management,
					           	result[i].channelName,result[i].channelType,result[i].createDate,
					           	result[i].isValid,result[i].noValidReason,result[i].isAssign,
					           	result[i].assignEmployee
					           ];
					t.row.add(obj).draw(false);
				}
			},
			error : function(data){
				alert("查询出错！ 请检查网络！");
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
        		<img alt="" src="image/logo.png">
      		</a>
		</div>
		<div class="collapse navbar-collapse" style="margin-left: auto;margin-right: auto;width: 70%;">
			<ul class="nav nav-pills">
				<li role="presentation"><a id="usernameShow" href="#">欢迎您:<%=username %></a></li>
  				<li role="presentation"><a href="toKF.action">未处理商机</a></li>
  				<li role="presentation"><a href="KF/tj.jsp">数据量统计</a></li>
  				<li role="presentation"><a href="KF/adduser.jsp">添加用户</a></li>
  				<li role="presentation" class="active"><a href="#">查询数据</a></li>
  				<li role="presentation"><a href="#">成单数据</a></li>
  				<li role="presentation"><a href="#">导入商机</a></li>
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
      			<input type="text" class="form-control" id="startDate" placeholder="起始时间" onclick="WdatePicker()">
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
  		<input type="button" class="btn btn-primary" id="exportOppInfo" value="导出" data-loading-text="查询中">
	</form>
</div>
<div class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
  <div class="panel-heading">商机信息</div>
  <table id="oppInfoTab" class="table table-striped table-bordered dataTable" style="width: 100%" aria-describedby="example_info" role="grid" cellspacing="0" width="100%">
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
<!-- <div id="queryDataDiv">
	<div id="searchDiv">
		起始时间：<input name="beginDate" type="text" id="beginDate" onclick="WdatePicker()"> &nbsp;&nbsp;&nbsp;&nbsp;
		截止时间：<input name="endDate" type="text" id="endDate" onclick="WdatePicker()">
		<button id="searchButton">查询</button>
		<button id="exportExcel">导出</button>
	</div>
	<br>
	
	<div id="queryTable">
		<table id="queryDataTable" cellpadding="0" cellspacing="0" border="0">
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
</div> -->
</body>
</html>