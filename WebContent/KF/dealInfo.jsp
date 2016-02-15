<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商机分销</title>

<link href="<%=request.getContextPath()%>/bootstrap-3.3.4-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bootstrap-3.3.4-dist/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var t = $("#dealInfoTab").DataTable({});
$(document).ready(function(){
	$.ajax({
		url : 'getCurtMonDealInfo.action',
		type : 'post',
		dataType : 'json',
		success : function(e){
			t.clear().draw(false);
			var data = eval("(" + e + ")");
			for (var i = 0; i < data.length; i++) {
				var obj = [
				           data[i].stuName,data[i].parentName,data[i].contactTel1,data[i].contactTel2,
				           data[i].channelName,data[i].cardCode,data[i].clsName,data[i].inDate,
				           data[i].management,data[i].pay,data[i].beginDate,data[i].endDate
				          ];
				t.row.add(obj).draw(false);
			}
		},
		error : function(e){
			alert("网络出错！请联系管理员！");
		}
	})
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
  				<li role="presentation"><a href="toKF.action">未处理商机</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/tj.jsp">数据量统计</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/adduser.jsp">添加用户</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/KF/queryOpp.jsp">查询数据</a></li>
  				<li role="presentation" class="active"><a href="#">成单数据</a></li>
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
  		<input type="button" class="btn btn-primary" id="dealSearchButton" value="查询" data-loading-text="查询中">
  		<input type="button" class="btn btn-primary" id="dealExportExcel" value="导出" data-loading-text="查询中">
	</form>
</div>
<div class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
  <div class="panel-heading">成单数据</div>
  <table id="dealInfoTab" class="table table-striped table-bordered dataTable" style="width: 100%" aria-describedby="example_info" role="grid" cellspacing="0" width="100%">
  		<thead>
  			<tr>
  				<th>学员姓名</th>
				<th>家长姓名</th>
				<th>联系方式1</th>
				<th>联系方式2</th>
				<th>渠道商</th>
				<th>听课证号</th>
				<th>班级名称</th>
				<th>进班日期</th>
				<th>管理部门</th>
				<th>学费</th>
				<th>开课日期</th>
				<th>结课日期</th>
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
				<th>渠道商</th>
				<th>听课证号</th>
				<th>班级名称</th>
				<th>进班日期</th>
				<th>管理部门</th>
				<th>学费</th>
				<th>开课日期</th>
				<th>结课日期</th>
  			</tr>
  		</tfoot>
  	</table>
</div>
</body>
</html>