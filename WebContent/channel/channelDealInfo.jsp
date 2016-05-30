<%@page import="com.xdf.dto.Management"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.Enumeration"%>
<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>商机分销系统</title>
<link href="<%=request.getContextPath()%>/css/channelMain.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/jquery.dataTables.min.css" rel="stylesheet" type="text/css">


<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="< =request.getContextPath()%>/js/channelDealInfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<%
	Enumeration<String> enumeration = session.getAttributeNames();
	String username = "";
	String channelname = "";
	String channeltype = "";
	List<Management> managementList = new ArrayList<Management>();
	boolean hasAttr = false;
	
	while(enumeration.hasMoreElements()){
		String attr = enumeration.nextElement();
		if("username".equals(attr)){
			hasAttr = true;
		}
	}
	PrintWriter printWriter = response.getWriter();
	if(!hasAttr){
		printWriter.print("<script>alert(\"请先登录！\")</script>");
		printWriter.print("<script>window.location.href = \"../login.jsp\"</script>");
	}else {
		//判断username是否有值
		username = session.getAttribute("username").toString();
		if("".equals(username) || username == null) {
			printWriter.print("<script>alert(\"请先登录！\")</script>");
			printWriter.print("<script>window.location.href = \"../login.jsp\"</script>");
		}else {
			String role = session.getAttribute("role").toString();
			if(!"4".equals(role)){
				printWriter.print("<script>alert(\"您没有权限访问该页面！\")</script>");
				printWriter.print("<script>window.location.href = \"../login.jsp\"</script>");
			}else {
				channelname = session.getAttribute("channelName").toString();
				channeltype = session.getAttribute("channelType").toString();
				managementList = (List<Management>)session.getAttribute("managementList");
			}
		}
	}
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
  				<li role="presentation"><a href="<%=request.getContextPath()%>/channel/channelMain.jsp">录入商机</a></li>
  				<li role="presentation" class="active"><a href="#">查看成单情况</a></li>
  				<li role="presentation"><a href="<%=request.getContextPath()%>/channel/importOpp.jsp">批量导入</a></li>
			</ul>
		</div>
	</div>
</nav>
<br/>
<div style="width: 90%;margin-left: auto;margin-right: auto;" class="well well-lg">
	<form class="form-inline">
  		<div class="form-group">
    		<label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
    		<div class="input-group">
      			<div class="input-group-addon">起始时间</div>
      			<input type="text" class="form-control" id="startDate" placeholder="起始时间" onclick="WdatePicker()">
    		</div>
  		</div>
  		&nbsp;&nbsp;&nbsp;&nbsp;
  		<div class="form-group">
    		<label class="sr-only" for ="exampleInputAmount">Amount (in dollars)</label>
    		<div class="input-group">  
      			<div class="input-group-addon">截止时间</div>
      			<input type="text" class="form-control" id="endDate" placeholder="截止时间" onclick="WdatePicker()">
    		</div>
  		</div>
  		&nbsp;&nbsp;&nbsp;&nbsp;
  		<div class="form-group">
    		<label class="sr-only" for="exampleInputAmount">Amount (in dollars)</label>
    		<div class="input-group">
      			<div class="input-group-addon">学员电话</div>
      			<input type="text" class="form-control" id="stuContactTel" placeholder="学员电话">
    		</div>
  		</div>
  		<input type="button" class="btn btn-primary" id="searchOppByChannel" value="查询" data-loading-text="查询中">
  		<input type="button" class="btn btn-success" id="exportOppDealInfo" value="导出">
	</form>
</div>
<div class="panel panel-primary" style="width: 90%;margin-left: auto;margin-right: auto;">
  <div class="panel-heading">成单情况</div>
  <table id="oppInfoTab" class="table table-striped table-bordered dataTable" style="width: 100%" aria-describedby="example_info" role="grid" cellspacing="0" width="100%">
  		<thead>
  			<tr>
  				<th>创建时间</th>
  				<th>学员姓名</th>
  				<th>家长姓名</th>
  				<th>联系方式1</th>
  				<th>联系方式2</th>
  				<th>需求课程</th>
  				<th>所属部门</th>
  				<th>是否有效</th>
  				<th>无效原因</th>
  				<th>成单情况</th>
  			</tr>
  		</thead>
  		<tbody>
  		</tbody>
  		<tfoot>
  			<tr>
  				<th>创建时间</th>
  				<th>学员姓名</th>
  				<th>家长姓名</th>
  				<th>联系方式1</th>
  				<th>联系方式2</th>
  				<th>需求课程</th>
  				<th>所属部门</th>
  				<th>是否有效</th>
  				<th>无效原因</th>
  				<th>成单情况</th>
  			</tr>
  		</tfoot>
  	</table>
</div>
</body>
</html>