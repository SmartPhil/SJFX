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
    		<label for="exampleInputFile">所属部门</label>
    		<select class="form-control" id="kfManagement" name="kfManagement">
    			<% for(int i = 0; i < managementList.size(); i++){ %>
						<option value="<%=managementList.get(i).getManagementName() %>"><%=managementList.get(i).getManagementName() %></option>
				<%	} %>
    		</select>
  		</div>
  		<div class="form-group">
    		<label id="showManagementExplain" class="btn btn-primary">查看部门说明</label>
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
<div class="modal fade" id="managementExplain" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">所属部门说明</h4>
      </div>
      <div class="modal-body"> 
       	<table class="table table-bordered">
       		<tr>
       			<td>部门名称</td>
       			<td>部门说明</td>
       		</tr>
       		<tr>
       			<td>优能中学部</td>
       			<td>12-18岁中学25人全科、冬夏令营</td>
       		</tr>
       		<tr>
       			<td>中学个性化</td>
       			<td>12-18岁中学1对1和6人及以下全科</td>
       		</tr>
       		<tr>
       			<td>英联邦项目部</td>
       			<td>IELTS</td>
       		</tr>
       		<tr>
       			<td>北美项目部</td>
       			<td>TOEFL、SAT、SSAT、GRE、GMAT、LSAT、AP</td>
       		</tr>
       		<tr>
       			<td>国内考试部</td>
       			<td>四六级，专四专八、考研、大学预科</td>
       		</tr>
       		<tr>
       			<td>英语学习部</td>
       			<td>新概念、口译、BEC、TOEIC、口语、冬夏令营</td>
       		</tr>
       		<tr>
       			<td>多语种部</td>
       			<td>德、法、日、韩、西班牙</td>
       		</tr>
       		<tr>
       			<td>泡泡少儿部</td>
       			<td>3-12岁少儿综合、冬夏令营</td>
       		</tr>
       		<tr>
       			<td>国际游学部门</td>
       			<td>全线路</td>
       		</tr>
       		<tr>
       			<td>其他</td>
       			<td>无需新东方跟进回访的商机</td>
       		</tr>
       	</table>
      </div>
    </div>
  </div>
</div>
</body>
</html>