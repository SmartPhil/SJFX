<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>成单数据</title>
<link href="<%=request.getContextPath()%>/css/KFMain.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/uploadify/uploadify.css" rel="stylesheet"  type="text/css" media="screen"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/tableExport.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var t = $("#dealTable").DataTable({});
	
	$.ajax({
		url : 'getCurtMonDealInfo.action',
		type : 'post',
		data : {"username" : $("#usernameShow").html().split(":")[1]},
		dataType : 'json',
		success : function(e){
			t.clear().draw(false);
			var data = eval("(" + e + ")");
		
			for (var i = 0; i < data.length; i++) {
				var rebatePer = data[i].rebate*1*100 + "%";
				var obj = [
				           data[i].createTime,
				           data[i].stuName,
				           data[i].contactTel1,
				           data[i].clsName,
				           data[i].inDate,
				           data[i].beginDate,
				           data[i].pay,
				           data[i].channelName,
				           data[i].management
				           ];
				t.row.add(obj).draw(false);
			}
		},
		error : function(e){
			alert("查询当月成单数据失败！");
		}
	});
	
	$("#searchButton").click(function(){
		$.ajax({
			url : 'searchDeal.action',
			type : 'post',
			dataType : 'json',
			data : {	
				'beginDate' : $("#beginDate").val(),
				'endDate' : $("#endDate").val(), 
				'stuContactTel' : $("#stuContactTel").val(),
				'username' : $("#usernameShow").html().split(":")[1]
			},
			success : function(e){
				t.clear().draw(false);
				var data = eval("(" + e + ")");
				for (var i = 0; i < data.length; i++) {
					var rebatePer = data[i].rebate*1*100 + "%";
					var obj = [
								data[i].createTime,
								data[i].stuName,
								data[i].contactTel1,
								data[i].clsName,
								data[i].inDate,
								data[i].beginDate,
								data[i].pay,
								data[i].channelName,
								data[i].management
					           ];
					t.row.add(obj).draw(false);
				}
			},
			error : function(e){
				alert("按条件查询成单数据失败！");
			}
		});
	});
	$("#exportExcel").click(function(){
		var param = "begin=" + $("#beginDate").val()
					+ "&end=" + $("#endDate").val()
					+ "&stuContactTel=" + $("#stuContactTel").val()
					+ "&username=" + $("#usernameShow").html().split(":")[1];
		location.href = "exportDealInfo.action?" + param;
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
  				<li role="presentation"  class="active"><a href="#">成单数据</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/searchopp.jsp">查询数据</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/SC/valid.jsp">有效性验证</a></li>
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
  		<input type="button" class="btn btn-primary" id="exportExcel" value="导出" data-loading-text="查询中">
	</form>
</div>
<br/>
<div class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
	<div class="panel-heading">成单数据</div>
	<table id="dealTable" class="table table-striped table-bordered dataTable" style="width: 100%" aria-describedby="example_info" role="grid" cellspacing="0" width="100%">
		<thead>
			<tr> 
				<th>接收时间</th>
				<th>姓名</th>
				<th>联系电话</th>
				<th>班级名称</th>
				<th>进班日期</th>
				<th>开班日期</th>
				<th>学费</th>
				<th>渠道商</th>
				<th>所属部门</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		<tfoot>
			<tr>
				<th>接收时间</th>
				<th>姓名</th>
				<th>联系电话</th>
				<th>班级名称</th>
				<th>进班日期</th>
				<th>开班日期</th>
				<th>学费</th>
				<th>渠道商</th>
				<th>所属部门</th>
			</tr>
		</tfoot>
	</table>
</div>
</body>
</html>