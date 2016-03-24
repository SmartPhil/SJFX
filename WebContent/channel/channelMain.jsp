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
    		<label for="exampleInputFile">需求课程</label>
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
  		<input type="hidden" value="<%=channelname %>" id="giveOrg" name="giveOrg"/>
  		<input type="hidden" value="<%=channeltype %>" id="channelType" name="channelType"/>
  		<div class="form-group">
    		<label for="exampleInputFile">所在区域</label>
    		<select class="form-control" id="area" name="area">
    			<option value="1">杨浦区</option>
    			<option value="2">徐汇区</option>
    			<option value="3">浦东区</option>
    			<option value="4">普陀区</option>
    			<option value="5">黄浦区</option>
    			<option value="6">长宁区</option>
    			<option value="7">静安区</option>
    			<option value="8">虹口区</option>
    			<option value="9">闸北区</option>
    			<option value="10">闵行区</option>
    			<option value="11">宝山区</option>
    			<option value="12">嘉定区</option>
    			<option value="13">松江区</option>
    			<option value="14">青浦区</option>
    			<option value="15">奉贤区</option>
    			<option value="16">金山区</option>
    			<option value="17">崇明区</option>
    			<option value="18">非上海</option>
    		</select>
  		</div>
		<div class="form-group">
    		<label for="exampleInputFile">联系地址</label>
    		<input type="text" class="form-control" id="address" name="address" placeholder="请输入学员地址">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">学校</label>
    		<input type="text" class="form-control" id="school" name="school" placeholder="请输入学员就读学校">
  		</div>
  		<div class="form-group">
    		<label for="exampleInputFile">年级</label>
    		<select class="form-control" id="grade" name="grade">
    			<option value="1">幼儿</option>
    			<option value="2">小班</option>
    			<option value="3">中班</option>
    			<option value="4">大班</option>
    			<option value="5">一年级</option>
    			<option value="6">二年级</option>
    			<option value="7">三年级</option>
    			<option value="8">四年级</option>
    			<option value="9">五年级</option>
    			<option value="10">六年级</option>
    			<option value="11">初一</option>
    			<option value="12">初二</option>
    			<option value="13">初三</option>
    			<option value="14">高一</option>
    			<option value="15">高二</option>
    			<option value="16">高三</option>
    			<option value="17">大一</option>
    			<option value="18">大二</option>
    			<option value="19">大三</option>
    			<option value="20">大四</option>
    			<option value="21">成人</option>
    		</select>
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